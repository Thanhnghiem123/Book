package server;

import dao.BookDao;
import dao.impl.BookImpl;
import entity.Book;
import org.hibernate.Hibernate;

import java.io.DataInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(4501)) {
            System.out.println("Server is running on port 4501");

            while (true) {
                Socket socket = server.accept();
                System.out.println("Client connected");
                System.out.println("Client IP: " + socket.getInetAddress().getHostName());

                Server temp = new Server();
                Thread t = new Thread(temp.new ClientHandler(socket));
                t.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private class ClientHandler implements Runnable {
        private Socket socket;
        private BookDao bookDao;

        public ClientHandler(Socket socket) throws RemoteException {
            super();
            this.socket = socket;
            bookDao = new BookImpl();
        }

        @Override
        public void run() {
            try {
                DataInputStream in = new DataInputStream(socket.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

                int choice = 0;

                while (true) {
                    choice = in.readInt();
                    switch (choice) {
                        //  Liệt kê danh sách các cuốn sách được viết bởi tác giả nào đó khi biết tên tác giả và
                        //có điểm đánh giá từ mấy sao trở lên.
                        case 1:
                            String author = in.readUTF();
                            int rating = in.readInt();
                            List<Book> books = bookDao.listRatedBooks(author, rating);
                            for (Book book : books) {
                                Hibernate.initialize(book.getAuthors());
                            }
                            out.writeObject(books);
                            break;
                        case 2:
                            Map<String, Long> listCountBooksByAuthor = bookDao.countBooksByAuthor();
                            out.writeObject(listCountBooksByAuthor);
                            break;
                        case 3:
                            // Candidate(String id, String fullName, int yearOfBirth, String gender, String email, String phone, String description, Set<Certificate> certificates, Position position)
                            String isbn = in.readUTF();
                            String readerID = in.readUTF();
                            int ratings = in.readInt();
                            String comment = in.readUTF();

                            boolean update = bookDao.updateReviews(isbn, readerID, ratings, comment);

                            out.writeObject(update);
                            break;
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
