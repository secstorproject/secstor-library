package com.ifsc.secstor.library.facade;

import com.ifsc.secstor.library.testutils.IndexKeyPair;
import com.ufsc.das.gcseg.pvss.exception.InvalidVSSScheme;
import com.ufsc.das.gcseg.secretsharing.SecretShareEngine;
import com.ufsc.das.gcseg.secretsharing.Shares;
import lombok.Getter;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Getter
/**
 * Classe responsável pela divisão e reconstrução dos segredos utilizando o algoritmo PVSS.
 *
 * @author Acacio.cding
 */
public class PVSSEngine implements Engine {
    private final SecretShareEngine engine;
    private Shares shares;

    public PVSSEngine(SecretShareEngine engine) {
        this.engine = engine;
    }

    /**
     * Método que realiza a divisão da informação em N segredos
     * @param data Informação que será dividida em N segredos
     */
    @Override
    public void split(String data) throws InvalidVSSScheme {
        this.shares = engine.split(data);
    }

    /**
     * Método que realiza a reconstrução dos pedaços utilizando no mínimo K número de pedaços
     * @return A informação reconstruída como era antes da divisão
     */
    @Override
    public String reconstruct() throws InvalidVSSScheme {
        return this.engine.combine(this.shares);
    }

    public String reconstruct(int keyNumber) throws InvalidVSSScheme {
        Shares kShares = new Shares(this.shares.getShares().stream()
                .filter(share -> share.index() < keyNumber)
                .collect(Collectors.toList()),
                this.shares.getModulus(),
                this.shares.getKey());

        return this.engine.combine(kShares);
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

        return new ArrayList<>(this.shares.getShares());
    }

    @Override
    public void setShares(Object shares) {
        this.shares = (Shares) shares;
    }
}
