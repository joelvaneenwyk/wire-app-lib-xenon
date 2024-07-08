package com.wire.xenon;

import com.wire.xenon.crypto.storage.IdentitiesDAO;
import com.wire.xenon.state.StatesDAO;
import java.util.UUID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DAOTest extends DatabaseTestBase {

    @Test
    public void testIdentitiesDAO() {
        final IdentitiesDAO identitiesDAO = jdbi.onDemand(IdentitiesDAO.class);
        final String id = UUID.randomUUID().toString();

        final int insert = identitiesDAO.insert(id, id.getBytes());
        assertEquals(insert, 1);

        final byte[] bytes = identitiesDAO.get(id).data;
        assert(bytes.length > 0);

        final int delete = identitiesDAO.delete(id);
        assertEquals(delete, 1);
    }

    @Test
    public void testStatesDAO() {
        final StatesDAO statesDAO = jdbi.onDemand(StatesDAO.class);
        final UUID id = UUID.randomUUID();
        final String text = "{\"some\" : \"text\"}";

        final int insert = statesDAO.insert(id, text);
        assertEquals(insert, 1);

        final String dbText = statesDAO.get(id);
        assertEquals(dbText, text);

        final int delete = statesDAO.delete(id);
        assertEquals(delete, 1);
    }
}
