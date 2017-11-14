package org.kvp_bld_sck.BookDatabase.dao.impl;

import org.kvp_bld_sck.BookDatabase.dao.exception.DaoException;

import java.io.*;
import java.util.Scanner;

public class FileUtils {

    public static File getFile(String path) throws DaoException {
        File dataFile = new File(path);
        if (!dataFile.exists())
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                throw new DaoException("cannot create file " + path, e);
            }
        return dataFile;
    }

    public static PrintStream getOut(String path) throws DaoException {
        try {
            return new PrintStream(new FileOutputStream(getFile(path)));
        } catch (FileNotFoundException e) {
            throw new DaoException("cannot open file " + path + " for write", e);
        }
    }

    public static Scanner getIn(String path) throws DaoException {
        try {
            return new Scanner(new FileInputStream(getFile(path)));
        } catch (FileNotFoundException e) {
            throw new DaoException("cannot open file " + path + " for read", e);
        }
    }

}
