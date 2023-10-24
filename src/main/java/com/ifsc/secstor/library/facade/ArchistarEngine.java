package com.ifsc.secstor.library.facade;

import com.at.archistar.crypto.CryptoEngine;
import com.at.archistar.crypto.data.ReconstructionResult;
import com.at.archistar.crypto.data.Share;
import com.at.archistar.crypto.secretsharing.ReconstructionException;
import com.ifsc.secstor.library.testutils.IndexKeyPair;
import lombok.Getter;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;

@Getter
/**
 * Classe responsável pela divisão e reconstrução dos segredos utilizando os algoritmos disponibilizados pelo archistar
 * <a href="#{https://github.com/Archistar/archistar-smc}">archistar</a>.
 *
 * @author Acacio.coding
 */
public class ArchistarEngine implements Engine {
    private final CryptoEngine engine;
    private Share[] shares;

    public ArchistarEngine(CryptoEngine engine) {
        this.engine = engine;
    }


    /**
     * Método que realiza a divisão da informação em N segredos
     * @param data Informação que será dividida em N segredos
     */
    @Override
    public void split(String data) {
        this.shares = engine.share(data.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Método que realiza a reconstrução da informação, onde K <= shares <= N
     * @return A informação reconstruída como era antes da divisão
     */
    @Override
    public String reconstruct() throws ReconstructionException {
        ReconstructionResult result = engine.reconstruct(this.shares);
        return new String(result.getData(), StandardCharsets.UTF_8);
    }

    public String reconstruct(int keyNumber) throws ReconstructionException {
        Share[] kShares = new Share[keyNumber];
        System.arraycopy(this.shares, 0, kShares, 0, kShares.length);
        ReconstructionResult result = engine.reconstruct(kShares);

        return new String(result.getData(), StandardCharsets.UTF_8);
    }


    @Override
    public String getEngine() {
        return this.engine.toString();
    }

    @Override
    public String getAlgorithm() {
        return engine.toString();
    }

    @Override
    public ArrayList<IndexKeyPair> getPieces() throws Exception {
        if (this.shares == null) {
            throw new Exception("This engine was not used yet.");
        }

        ArrayList<IndexKeyPair> toReturn = new ArrayList<>();

        for (Share share : this.shares) {
            toReturn.add(new IndexKeyPair(share.getX(), Base64.getEncoder().encodeToString(share.getYValues())));
        }

        return toReturn;
    }

    @Override
    public void setShares(Object shares) {
        this.shares = (Share[]) shares;
    }
}
