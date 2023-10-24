package com.ifsc.secstor.library.facade;

import com.at.archistar.crypto.CSSEngine;
import com.at.archistar.crypto.KrawczykEngine;
import com.at.archistar.crypto.PSSEngine;
import com.at.archistar.crypto.ShamirEngine;
import com.at.archistar.crypto.secretsharing.WeakSecurityException;
import com.ufsc.das.gcseg.secretsharing.SecretShareEngine;
import lombok.Getter;

import java.security.NoSuchAlgorithmException;

@Getter
/**
 * Classe responsável pela centralização das bibliotecas servindo como uma fachada para utilização dos algoritmos
 * de compartilhamento de segredos seguindo a mesma estrutura de código.
 *
 * @author Acacio.coding
 */
public class EngineMaker {
    private final Engine shamir;
    private final Engine pss;
    private final Engine css;
    private final Engine krawczyk;
    private final Engine pvss;
    private final int n;
    private final int k;

    public EngineMaker(int n, int k) throws WeakSecurityException, NoSuchAlgorithmException {
        this.shamir = new ArchistarEngine(new ShamirEngine(n, k));
        this.pss = new ArchistarEngine(new PSSEngine(n, k));
        this.css = new ArchistarEngine(new CSSEngine(n, k));
        this.krawczyk = new ArchistarEngine(new KrawczykEngine(n, k));
        this.pvss = new PVSSEngine(new SecretShareEngine(n, k));
        this.n = n;
        this.k = k;
    }

    /**
     * Método que realiza a divisão da informação em N segredos
     * @param data Informação que será dividida em N segredos
     * @param algorithm Algoritmo utilizado no processo (shamir, pss, css, krawczyk, pvss)
     * @throws Exception Caso o algoritmo não seja válido entre as opções presentes na biblioteca
     */
    public void split(String data, String algorithm) throws Exception {
        if (algorithm.equalsIgnoreCase("shamir"))
            this.shamir.split(data);
        else if (algorithm.equalsIgnoreCase("pss"))
            this.pss.split(data);
        else if (algorithm.equalsIgnoreCase("css"))
            this.css.split(data);
        else if (algorithm.equalsIgnoreCase("krawczyk"))
            this.krawczyk.split(data);
        else if (algorithm.equalsIgnoreCase("pvss"))
            this.pvss.split(data);
        else
            throw new Exception("Algorithm didn't match any of valid types");
    }

    /**
     * Método que realiza a reconstrução dos pedaços utilizando no mínimo K número de pedaços
     * @param algorithm Algoritmo utilizado no processo (shamir, pss, css, krawczyk, pvss)
     * @return A informação reconstruída como era antes da divisão
     * @throws Exception Caso a informação não tenha sido dividida antes e portanto não existam segredos válidos para reconstruir
     */
    public String reconstruct(String algorithm) throws Exception {
        if (algorithm.equalsIgnoreCase("shamir") && !this.shamir.getPieces().isEmpty())
            return this.shamir.reconstruct();

        if (algorithm.equalsIgnoreCase("pss") && !this.pss.getPieces().isEmpty())
            return this.pss.reconstruct();

        if (algorithm.equalsIgnoreCase("css") && !this.css.getPieces().isEmpty())
            return this.css.reconstruct();

        if (algorithm.equalsIgnoreCase("krawczyk") && !this.krawczyk.getPieces().isEmpty())
            return this.krawczyk.reconstruct();

        if (algorithm.equalsIgnoreCase("pvss") && !this.pvss.getPieces().isEmpty())
            return this.pvss.reconstruct();

        throw new Exception("You need to split the shares in order to reconstruct them");
    }

    public String reconstruct(String algorithm, int keyNumber) throws Exception {
        if (algorithm.equalsIgnoreCase("shamir") && !this.shamir.getPieces().isEmpty())
            return ((ArchistarEngine) this.shamir).reconstruct(keyNumber);

        if (algorithm.equalsIgnoreCase("pss") && !this.pss.getPieces().isEmpty())
            return ((ArchistarEngine) this.pss).reconstruct(keyNumber);

        if (algorithm.equalsIgnoreCase("css") && !this.css.getPieces().isEmpty())
            return ((ArchistarEngine) this.css).reconstruct(keyNumber);

        if (algorithm.equalsIgnoreCase("krawczyk") && !this.krawczyk.getPieces().isEmpty())
            return ((ArchistarEngine) this.krawczyk).reconstruct(keyNumber);

        if (algorithm.equalsIgnoreCase("pvss") && !this.pvss.getPieces().isEmpty())
            return ((PVSSEngine) this.pvss).reconstruct(keyNumber);

        throw new Exception("You need to split the shares in order to reconstruct them");
    }
}
