package com.ufsc.das.gcseg.secretsharing;

import com.ifsc.secstor.library.testutils.IndexKeyPair;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigInteger;
import java.util.List;

@AllArgsConstructor
@Getter
public class Shares {

    private final List<IndexKeyPair> shares;
    private final BigInteger modulus;
    private final String Key;

    @Override
    public String toString() {
        return "PVSS";
    }
}
