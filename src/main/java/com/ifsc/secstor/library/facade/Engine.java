package com.ifsc.secstor.library.facade;

import com.at.archistar.crypto.secretsharing.ReconstructionException;
import com.ifsc.secstor.library.testutils.IndexKeyPair;
import com.ufsc.das.gcseg.pvss.exception.InvalidVSSScheme;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public interface Engine {
    void split(String data) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidVSSScheme;

    String reconstruct() throws UnsupportedEncodingException, InvalidVSSScheme, ReconstructionException;

    String getEngine();

    ArrayList<IndexKeyPair> getPieces() throws Exception;

    String getAlgorithm();

    void setShares(Object shares);
}
