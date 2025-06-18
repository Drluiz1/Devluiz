import java.util.*;

class Produto {
    int id;
    String nome;
    double preco;
    int estoque;

    Produto(int id, String nome, double preco, int estoque) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
    }

    @Override
    public String toString() {
        return "[" + id + "] " + nome + " | Preço: R$ " + preco + " | Estoque: " + estoque;
    }
}

class Venda {
    Produto produto;
    int quantidadeVendida;
    double totalVenda;

    Venda(Produto produto, int quantidadeVendida, double totalVenda) {
        this.produto = produto;
        this.quantidadeVendida = quantidadeVendida;
        this.totalVenda = totalVenda;
    }

    @Override
    public String toString() {
        return produto.nome + " x" + quantidadeVendida + " = R$ " + String.format("%.2f", totalVenda);
    }
}

public class SistemaDeVendasAgro {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Produto> produtos = new ArrayList<>();
        ArrayList<Venda> vendas = new ArrayList<>();
        int opcao;

        // Produtos prontos
        produtos.add(new Produto(1, "Milho", 45.50, 100));
        produtos.add(new Produto(2, "Soja", 120.00, 80));
        produtos.add(new Produto(3, "Trigo", 70.30, 60));
        produtos.add(new Produto(4, "Algodão", 95.75, 50));
        produtos.add(new Produto(5, "Café", 130.00, 40));

        do {
            System.out.println("\n=== SISTEMA DE VENDAS ===");
            System.out.println("1 - Listar produtos");
            System.out.println("2 - Vender produto");
            System.out.println("3 - Ver relatório de vendas");
            System.out.println("0 - Sair");
            System.out.print("Digite a opção desejada: ");
            opcao = sc.nextInt();
            sc.nextLine(); // limpar buffer

            switch (opcao) {
                case 1 -> listarProdutos(produtos);
                case 2 -> venderProduto(produtos, vendas, sc);
                case 3 -> mostrarRelatorio(vendas);
                case 0 -> System.out.println("Encerrando o sistema...");
                default -> System.out.println("Opção inválida. Tente novamente.");
            }

        } while (opcao != 0);

        sc.close();
    }

    public static void listarProdutos(ArrayList<Produto> produtos) {
        System.out.println("\n--- Produtos Disponíveis ---");
        for (Produto p : produtos) {
            System.out.println(p);
        }
    }

    public static void venderProduto(ArrayList<Produto> produtos, ArrayList<Venda> vendas, Scanner sc) {
        listarProdutos(produtos);

        System.out.print("\nDigite o ID do produto que deseja vender: ");
        int id = sc.nextInt();
        sc.nextLine();

        Produto selecionado = null;
        for (Produto p : produtos) {
            if (p.id == id) {
                selecionado = p;
                break;
            }
        }

        if (selecionado == null) {
            System.out.println("Produto não encontrado.");
            return;
        }

        System.out.print("Digite a quantidade vendida: ");
        int qtd = sc.nextInt();
        sc.nextLine();

        if (qtd <= 0 || qtd > selecionado.estoque) {
            System.out.println("Quantidade inválida ou estoque insuficiente.");
            return;
        }

        double total = qtd * selecionado.preco;
        selecionado.estoque -= qtd;
        vendas.add(new Venda(selecionado, qtd, total));

        System.out.println("Venda registrada com sucesso! Total: R$ " + String.format("%.2f", total));
    }

    public static void mostrarRelatorio(ArrayList<Venda> vendas) {
        System.out.println("\n--- Relatório de Vendas ---");

        if (vendas.isEmpty()) {
            System.out.println("Nenhuma venda registrada.");
            return;
        }

        Map<String, Integer> quantidadePorProduto = new HashMap<>();
        Map<String, Double> totalPorProduto = new HashMap<>();
        double totalGeral = 0;

        for (Venda v : vendas) {
            String nome = v.produto.nome;
            quantidadePorProduto.put(nome, quantidadePorProduto.getOrDefault(nome, 0) + v.quantidadeVendida);
            totalPorProduto.put(nome, totalPorProduto.getOrDefault(nome, 0.0) + v.totalVenda);
            totalGeral += v.totalVenda;
        }

        for (String nome : quantidadePorProduto.keySet()) {
            System.out.println(nome + " - Quantidade vendida: " + quantidadePorProduto.get(nome) +
                    " | Total: R$ " + String.format("%.2f", totalPorProduto.get(nome)));
        }

        System.out.println("Total geral vendido: R$ " + String.format("%.2f", totalGeral));
    }
}
