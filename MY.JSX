import { useState } from "react";

function ChatBubble({ message, isUser }) {
  return (
    <div className={`flex ${isUser ? "justify-end" : "justify-start"} my-2`}>
      <div className={`p-3 rounded-2xl max-w-sm ${isUser ? "bg-blue-600 text-white" : "bg-gray-300 text-black"}`}>
        {message}
      </div>
    </div>
  );
}

function App() {
  const [messages, setMessages] = useState([]);
  const [input, setInput] = useState("");

  const sendMessage = async () => {
    if (!input.trim()) return;

    const userMessage = { text: input, isUser: true };
    setMessages((prev) => [...prev, userMessage]);
    setInput("");

    const res = await fetch("https://api.openai.com/v1/chat/completions", {
      method: "POST",
      headers: {
        "Authorization": "Bearer SUA_CHAVE_DA_API_AQUI",
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        model: "gpt-3.5-turbo",
        messages: [{ role: "user", content: input }],
      }),
    });

    const data = await res.json();
    const reply = data.choices?.[0]?.message?.content;

    if (reply) {
      setMessages((prev) => [...prev, { text: reply.trim(), isUser: false }]);
    }
  };

  return (
    <div className="h-screen bg-white p-4 flex flex-col">
      <div className="flex-1 overflow-y-auto mb-4">
        {messages.map((msg, i) => (
          <ChatBubble key={i} message={msg.text} isUser={msg.isUser} />
        ))}
      </div>
      <div className="flex">
        <input
          className="flex-1 border border-gray-400 rounded-l-lg p-2"
          value={input}
          onChange={(e) => setInput(e.target.value)}
          onKeyDown={(e) => e.key === "Enter" && sendMessage()}
        />
        <button onClick={sendMessage} className="bg-blue-600 text-white px-4 rounded-r-lg">
          Enviar
        </button>
      </div>
    </div>
  );
}

export default App;
