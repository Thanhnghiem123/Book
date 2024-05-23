import entity.Book;
import org.hibernate.Hibernate;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 4501);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("Choose an option:");
                System.out.println("1. Liệt kê danh sách các cuốn sách được viết bởi tác giả nào đó khi biết tên tác giả và\n" +
                        "có điểm đánh giá từ mấy sao trở lên.");
                System.out.println("2. Thống kê số cuốn sách được dịch sang ngôn ngữ khác của từng tác giả, kết quả sắp\n" +
                        "xếp theo tên tác giả.");
                System.out.println("3. Cập nhật thêm một lượt đánh giá cho một cuốn sách, cập nhật thành công nếu cuốn\n" +
                        "sách và người đọc đã tồn tại, rating phải từ 1 đến 5 và bình luận không được để trống hay rỗng.");
                System.out.println("4. Exit");

                int option = scanner.nextInt();
                scanner.nextLine();  // consume newline left-over

                out.writeInt(option);
                switch (option) {
                    case 1:
                        System.out.println("Nhap author: ");
                        String author = scanner.nextLine();
                        System.out.println("Nhap rating: ");
                        int rating = scanner.nextInt();

                        out.writeUTF(author);
                        out.writeInt(rating);
                        List<Book> books = (List<Book>) in.readObject();


                        for (Book book : books) {
                            Hibernate.initialize(book.getAuthors());
                        }

                        if (books.isEmpty()) {
                            System.out.println("Khong co sach.");
                        } else {
                            System.out.println("Sach can tim: ");
                            for (Book book : books) {
                                System.out.println(book.toString());
                            }
                        }
                        break;
                    case 2:
                        Map<String, Long> listCandidatesByCompanies = (Map<String, Long>) in.readObject();
                        listCandidatesByCompanies.forEach((name, value) ->
                                System.out.println("Name: " + name + ", Value: " + value)
                        );
                        break;

                    case 3:
                        System.out.println("Nhap isbn: ");
                        String isbn = scanner.nextLine();
                        System.out.println("Nhap readerID: ");
                        String readerID = scanner.nextLine();
                        System.out.println("Nhap rating: ");
                        int rating1 = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Nhap comment: ");
                        String comment = scanner.nextLine();

                        out.writeUTF(isbn);
                        out.writeUTF(readerID);
                        out.writeInt(rating1);
                        out.writeUTF(comment);

                        boolean update = (boolean) in.readObject();
                        System.out.println(update);

                        break;


                    case 4:
                        System.out.println("Exiting...");
                        System.exit(0);
                    default:
                        System.out.println("Invalid option. Please choose again.");
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
