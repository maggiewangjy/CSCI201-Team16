public class ParticipantsDatabaseUtil {
    public Member addMember(Member member) throws SQLException {
        String sql = "INSERT INTO participants (firstName, lastName, email) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, member.getFirstName());
            stmt.setString(2, member.getLastName());
            stmt.setString(3, member.getEmail()); 
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Creating member failed, no rows affected.");
            }
            
            // try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
            //     if (generatedKeys.next()) {
            //         member.setUserId(generatedKeys.getInt(1));
            //     } else {
            //         throw new SQLException("Creating user failed, no ID obtained.");
            //     }
            // }
        }
        
        return member;
    }
}
