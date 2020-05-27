package co.ecso.dacato.hsql;

/**
 * MysqlToHsqlMapFilter.
 *
 * @author Christian Scharmach (cs@e-cs.co)
 * @version $Id:$
 * @since 25.04.16
 */
final class MysqlToHsqlMapFilter {

    private MysqlToHsqlMapFilter() {
        //not needed
    }

    static String filter(final String s) {
        return s
                .replaceAll("/\\*.*?\\*/", "")
                .replaceAll("`|´", "")
                .replaceAll("(TINY|BIG)INT", "INTEGER")
                .replaceAll("\\) ENGINE.*?;", ")")
                .replaceAll("VARCHAR|varchar", "CHAR(255)")
                .replaceAll("LONGTEXT|longtext", "CHAR(255)")
                .replaceAll("([a-zA-Z]+)\\([0-9]+\\)", "$1")
                .replaceAll("AUTO_INCREMENT", "generated by default as identity")
                .replaceAll("(ENUM|enum)\\(.*?\\)", "CHAR(255)")
                .replaceAll("((?:NOT )?NULL)[\\s ]DEFAULT[\\s ](.*?)([\\s, ])", "default $2 $1 $3")
                .replaceAll("char ", "char(255) ")
                .replaceAll("CREATE DATABASE (.*?);", "create schema $1 authorization dba;")
                .replaceAll("\\),\\)", "))");
    }

}
