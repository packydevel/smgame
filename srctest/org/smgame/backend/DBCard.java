package org.smgame.backend;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import org.smgame.util.Common;
import org.smgame.util.Logging;



public class DBCard {
    private final String tableCard = "CARDS";
    private final String col1Card = "id";
    private final String col2Card = "suit";
    private final String col3Card = "point";

    public int selectIdCard(String suit, String point) throws ClassNotFoundException,
                                                        SQLException, IOException, Exception{
        Connection conn = DBAccess.getConnection();
        String sql = "SELECT " + col1Card + " FROM " + tableCard + " WHERE " + col2Card +
                    "=? AND " + col3Card + "=?;";
        PreparedStatement prpstmt = conn.prepareStatement(sql);
        Common.setParameter(prpstmt, 1, suit, Types.VARCHAR);
        Common.setParameter(prpstmt, 2, suit, Types.VARCHAR);
        Logging.logInfo(prpstmt.toString());

        return -1;
    }

}
