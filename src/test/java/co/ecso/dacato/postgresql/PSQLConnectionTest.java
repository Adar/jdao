package co.ecso.dacato.postgresql;

import org.junit.After;
import org.junit.Before;

/**
 * PSQLConnectionTest
 *
 * @author Christian Scharmach (cs@e-cs.co)
 * @version $Id:$
 * @since 07.10.16
 */
@SuppressWarnings("unused")
public final class PSQLConnectionTest extends AbstractPSQLTest {
    @Before
    public void setUp() throws Exception {
        this.setUpPSQLDatabase();
    }

    @After
    public void tearDown() throws Exception {
        this.cleanupPostgreSQLDatabase();
    }
}
