package com.ifsc.secstor.library.testutils;

import com.google.common.collect.Multimap;

public record MapAlgorithmKeyNumber(String algorithm, Multimap<Integer, String> timings, String keyNumber) {
}
