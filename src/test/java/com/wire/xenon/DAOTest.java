package com.wire.xenon;

import com.wire.xenon.crypto.storage.IdentitiesDAO;
import com.wire.xenon.state.StatesDAO;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class DAOTest extends DatabaseTestBase {

    @Test
    @SuppressWarnings("unused")
    public void testIdentitiesDAO() {
        final IdentitiesDAO identitiesDAO = jdbi.onDemand(IdentitiesDAO.class);
        final String id = UUID.randomUUID().toString();

        final int insert = identitiesDAO.insert(id, id.getBytes());
        final byte[] bytes = identitiesDAO.get(id).data;
        final int delete = identitiesDAO.delete(id);
    }

    @Test
    @SuppressWarnings("unused")
    public void testStatesDAO() {
        final StatesDAO statesDAO = jdbi.onDemand(StatesDAO.class);
        final UUID id = UUID.randomUUID();
        final String text = "{\"some\" : \"text\"}";

        final int insert = statesDAO.insert(id, text);
        final String dbText = statesDAO.get(id);
        final int delete = statesDAO.delete(id);
    }
}
