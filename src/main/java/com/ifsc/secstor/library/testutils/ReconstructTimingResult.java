package com.ifsc.secstor.library.testutils;

import java.util.List;

public record ReconstructTimingResult(String threadId, List<List<MapAlgorithmKeyNumber>> results) {
}
