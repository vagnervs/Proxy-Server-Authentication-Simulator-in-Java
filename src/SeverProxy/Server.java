package SeverProxy;

import entidade.Usuario;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private final List< Usuario> listUsuario = new ArrayList<>();
    private ServerSocket serverSocket;
    private BufferedReader input;
    private PrintWriter output;

    public void criarServerSocket(int porta) throws IOException {
        serverSocket = new ServerSocket(porta);
    }

    public Socket esperarConexao() throws IOException {
        Socket socket = serverSocket.accept();
        return socket;
    }

    public void fecharSocket(Socket s) throws IOException {
        if (s != null) {
            s.close();
        }

    }

    public void finalizarServidor() throws IOException {
        if (serverSocket != null) {
            serverSocket.close();
        }

    }

    public void tratarConexao(Socket socket) throws IOException {

        try {
            // Adiciona Usuário no Servidor

            Usuario usuario = new Usuario();
            usuario.setNome("maria");
            usuario.setSenha("123");
            listUsuario.add(usuario);

            usuario = new Usuario();
            usuario.setNome("vagner");
            usuario.setSenha("12345");
            listUsuario.add(usuario);

            usuario = new Usuario();
            usuario.setNome("mariana");
            usuario.setSenha("1234");
            listUsuario.add(usuario);

            usuario = new Usuario();
            usuario.setNome("lucas");
            usuario.setSenha("1234");
            listUsuario.add(usuario);

            //Leitor de buffer aberto para leitura de dados do cliente
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String username = input.readLine();
            System.out.println("SERVER SIDE: " + username);
            String password = input.readLine();
            System.out.println("SERVER SIDE: " + password);

            //printwriter aberto para gravar dados para o cliente
            output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

            boolean statusLogin = false;
            // Compara Dados do cliente com os dados do servidor 
            for (int i = 0; i < listUsuario.size(); i++) {

                if ((username.equals(listUsuario.get(i).getNome())
                        && password.equals(listUsuario.get(i).getSenha()))) {
                    statusLogin = true;
                }
            }

            if (statusLogin == true) {
                output.println("Welcome, " + username);
            } else {
                output.println("Login Failed");
            }
            output.flush();

        } catch (IOException e) {
            System.out.println("Problema no tratamento de conexão com cliente: " + socket.getInetAddress());
            System.out.println("ERRO: " + e.getMessage());
        } finally {
            //fechar socket de comunicaçao entre cliente e servidor.. 
        }
    }

}
