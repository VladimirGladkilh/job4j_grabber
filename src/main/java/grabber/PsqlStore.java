package grabber;



import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class PsqlStore implements Store, AutoCloseable {

    private Connection cnn;

    public PsqlStore(Properties cfg) throws SQLException {
        try {
            Class.forName(cfg.getProperty("driver-class-name"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        cnn = DriverManager.getConnection(
                cfg.getProperty("url"),
                cfg.getProperty("username"),
                cfg.getProperty("password"));
    }

    @Override
    public void save(Post post) {
        try (PreparedStatement st = cnn.prepareStatement("insert into post (subject, link, description, create_date) values (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);){
            st.setString(1, post.getSubject());
            st.setString(2, post.getLink());
            st.setString(3, post.getDescription());
            st.setTimestamp(4,new Timestamp(post.getCreate_date().getTime()));
            st.executeUpdate();
            try (ResultSet generatedKeys = st.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    post.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Post> getAll() {
        List<Post> postList = new LinkedList<>();
        try (PreparedStatement st = cnn.prepareStatement("select * from post");){
            st.executeQuery();
            ResultSet rs = st.getResultSet();
            while (rs.next()) {
                Post post = new Post( rs.getString(2), rs.getString(3), rs.getString(4), rs.getDate(5));
                post.setId(rs.getInt(1));
                postList.add(post);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

        }
        return postList;
    }

    @Override
    public Post findById(String id) {
        Post post = null;
        try (PreparedStatement st = cnn.prepareStatement("select * from post where id = ?");){
            st.setInt(1, Integer.valueOf(id));
            st.executeQuery();
            ResultSet rs = st.getResultSet();
            while (rs.next()) {
                post = new Post( rs.getString(2), rs.getString(3), rs.getString(4), rs.getDate(5));
                post.setId(rs.getInt(1));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

        }
        return post;
    }

    @Override
    public void close() throws Exception {
        if (cnn != null) {
            cnn.close();
        }
    }

    public static void main(String[] args) throws SQLException {
        try (InputStream in = PsqlStore.class.getClassLoader().getResourceAsStream("rabbit.properties")) {
            Properties config = new Properties();
            config.load(in);
            PsqlStore psqlStore = new PsqlStore(config);
            Post newPost = new Post("Subj6", "Link6");
            System.out.println(newPost.toString());
            psqlStore.save(newPost);
            Post findPost =  psqlStore.findById("1");
            System.out.println(findPost.toString());
            List<Post> postList = psqlStore.getAll();
            postList.forEach(post -> System.out.println(post.toString()));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

}