require('dotenv').config();
const express = require('express');
const cors = require('cors');
const { Configuration, OpenAIApi } = require('openai');

const app = express();
app.use(cors());
app.use(express.json());

const configuration = new Configuration({
  apiKey: process.env.OPENAI_API_KEY, 
});
const openai = new OpenAIApi(configuration);

app.post('/chat', async (req, res) => {
  const userMessage = req.body.message;

  try {
    const completion = await openai.createChatCompletion({
      model: 'gpt-3.5-turbo',
      messages: [
        { role: 'system', content: '' },
        { role: 'user', content: userMessage },
      ],
    });

    res.json({ reply: completion.data.choices[0].message.content });
  } catch (error) {
    console.error(error);
    res.status(500).send('Erro na API OpenAI');
  }
});

app.listen(3000, () => {
  console.log('Servidor rodando na porta 3000');
});

function toggleChat() {
  const chatbox = document.getElementById("chatbot-box");
  chatbox.classList.toggle("active");
}

document.getElementById('userInput').addEventListener('keydown', function(e) {
  if (e.key === 'Enter') {
    e.preventDefault();
    responder();
  }
});

function adicionarMensagem(texto, tipo = 'bot') {
  const chat = document.getElementById("chatbot-chat");
  const msgDiv = document.createElement('div');
  msgDiv.classList.add('message', tipo);
  msgDiv.textContent = texto;
  chat.appendChild(msgDiv);
  chat.scrollTop = chat.scrollHeight;
}

const inputField = document.getElementById('userInput');
const sendButton = document.getElementById('sendButton');

inputField.addEventListener('input', () => {
  sendButton.disabled = inputField.value.trim() === '';
});
sendButton.disabled = true; 
function toggleTheme() {
  if(document.body.getAttribute('data-theme') === 'dark') {
    document.body.removeAttribute('data-theme');
  } else {
    document.body.setAttribute('data-theme', 'dark');
  }
}
function toggleTheme() {
  if(document.body.getAttribute('data-theme') === 'dark') {
    document.body.removeAttribute('data-theme');
  } else {
    document.body.setAttribute('data-theme', 'dark');
  }
}
