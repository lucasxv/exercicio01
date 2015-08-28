package br.senac.tads.pi3.lucasbarbosa.exercicio1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Contatos {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String Nome;
        String Telefone;
        String Email;
        String Data;
        Contatos cont = new Contatos();
        do {

            System.out.println("-------- DIGITE UMA OPÇÃO --------");
            System.out.println("1- Listar");
            System.out.println("2- Cadastrar novo registro");
            System.out.println("3- Alterar registro");
            System.out.println("4- Excluir registro");

            int menu = sc.nextInt();

            if (menu == 1) {
                cont.listarPessoas();
            } else if (menu == 2) {
                cont.incluirPessoa();
            } else if (menu == 3) {
                cont.alterarPessoa();
            } else if (menu == 4) {
                cont.deletarPessoa();
            } else if (menu == 9) {
                System.exit(0);
            } else {
                System.out.println("OPÇÃO INVÁLIDA.");
            }

        } while (true);

    }

    public void incluirPessoa() {
        PreparedStatement stmt = null;
        Connection conn = null;

        String nome;
        Date dataNasc;
        String email;
        String telefone;

        // ENTRADA DE DADOS
        Scanner entrada = new Scanner(System.in);
        System.out.print("Digite o nome da pessoa: ");
        nome = entrada.nextLine();

        System.out.print("Digite a data de nascimento no formato dd/mm/aaaa: ");
        String strDataNasc = entrada.nextLine();
        DateFormat formatadorData = new SimpleDateFormat("dd/MM/yyyy");
        try {
            dataNasc = formatadorData.parse(strDataNasc);
        } catch (ParseException ex) {
            Logger.getLogger(Contatos.class.getName()).log(Level.SEVERE, null, ex);
            dataNasc = new Date();
        }
        System.out.print("Digite o telefone no formato 99 99999-9999: ");
        telefone = entrada.nextLine();

        System.out.print("Digite o e-mail: ");
        email = entrada.nextLine();

        String sql = "INSERT INTO TB_PESSOA (NM_PESSOA, DT_NASCIMENTO, VL_TELEFONE, VL_EMAIL) VALUES (?, ?, ?, ?)";
        try {
            conn = obterConexao();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.setDate(2, new java.sql.Date(dataNasc.getTime()));
            stmt.setString(3, telefone);
            stmt.setString(4, email);
            //stmt.setDate(5, new java.sql.Date(System.currentTimeMillis()));
            stmt.executeUpdate();
            System.out.println("Registro incluido com sucesso.");

        } catch (SQLException ex) {
            Logger.getLogger(Contatos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Contatos.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Contatos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Contatos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void listarPessoas() {
        Statement stmt = null;
        Connection conn = null;

        String sql = "SELECT ID_PESSOA, NM_PESSOA, DT_NASCIMENTO, VL_TELEFONE, VL_EMAIL FROM TB_PESSOA";
        try {
            conn = obterConexao();
            stmt = conn.createStatement();
            ResultSet resultados = stmt.executeQuery(sql);

            DateFormat formatadorData = new SimpleDateFormat("dd/MM/yyyy");

            while (resultados.next()) {
                Long id = resultados.getLong("ID_PESSOA");
                String nome = resultados.getString("NM_PESSOA");
                Date dataNasc = resultados.getDate("DT_NASCIMENTO");
                String email = resultados.getString("VL_EMAIL");
                String telefone = resultados.getString("VL_TELEFONE");
                System.out.println(String.valueOf(id) + ", " + nome + ", " + formatadorData.format(dataNasc) + ", " + email + ", " + telefone);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Contatos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Contatos.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Contatos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Contatos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void alterarPessoa() {

        PreparedStatement stmt = null;
        Connection conn = null;

        String nome;
        Date dataNasc;
        String email;
        String telefone;

        Scanner entrada = new Scanner(System.in);
        System.out.print("Digite o id da pessoa: ");
        int id = entrada.nextInt();

        System.out.print("Digite o nome da pessoa: ");
        entrada.nextLine();
        nome = entrada.nextLine();

        System.out.print("Digite a data de nascimento no formato dd/mm/aaaa: ");
        String strDataNasc = entrada.nextLine();
        DateFormat formatadorData = new SimpleDateFormat("dd/MM/yyyy");
        try {
            dataNasc = formatadorData.parse(strDataNasc);
        } catch (ParseException ex) {
            Logger.getLogger(Contatos.class.getName()).log(Level.SEVERE, null, ex);
            dataNasc = new Date();
        }
        System.out.print("Digite o telefone no formato 99 99999-9999: ");
        telefone = entrada.nextLine();

        System.out.print("Digite o e-mail: ");
        email = entrada.nextLine();

        int op;
        System.out.println("Deseja alterar o Contato?\n1 - Sim\n2 - Não");
        do {
            op = entrada.nextInt();
            if (op == 2) {
                System.out.println("Operação Cancelada!");
                return;
            }
            if (op != 1 && op != 2) {
                System.out.print("OPÇÃO INVÁLIDA.\nDigite uma opção válida: ");
            }
        } while (op != 1);

        String sql = "UPDATE TB_PESSOA SET NM_PESSOA = ?, DT_NASCIMENTO = ?, VL_TELEFONE = ?, VL_EMAIL = ? WHERE ID_PESSOA = ?";
        try {
            conn = obterConexao();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.setDate(2, new java.sql.Date(dataNasc.getTime()));
            stmt.setString(3, telefone);
            stmt.setString(4, email);
            stmt.setInt(5, id);
            stmt.executeUpdate();
            System.out.println("Registro alterado com sucesso.");

        } catch (SQLException ex) {
            Logger.getLogger(Contatos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Contatos.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Contatos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Contatos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void deletarPessoa() {
        PreparedStatement stmt = null;
        Connection conn = null;

        String nome;
        Date dataNasc;
        String email;
        String telefone;

        Scanner entrada = new Scanner(System.in);
        System.out.print("Digite o id da pessoa: ");
        int id = entrada.nextInt();

        String sql = "DELETE FROM TB_PESSOA WHERE ID_PESSOA = ? ";

        try {
            conn = obterConexao();
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, id);
            stmt.executeUpdate();

            System.out.println("Registro excluido com sucesso");

        } catch (SQLException ex) {
            Logger.getLogger(Contatos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Contatos.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Contatos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Contatos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private Connection obterConexao() throws SQLException, ClassNotFoundException {
        Connection conn = null;
        Class.forName("org.apache.derby.jdbc.ClientDataSource");
        conn = DriverManager.getConnection("jdbc:derby://localhost:1527/agendabd;SecurityMechanism=3", "app", "app");
        return conn;
    }

}
