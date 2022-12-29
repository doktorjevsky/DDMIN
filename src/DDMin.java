import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Static class for minimizing failing input for a given test function.
 * The ddMin applies a DFS on the input and returns a smallest subset that fails the test given.
 */

public class DDMin {

    // TODO: testing (he he how ironic :))


    public static <T> List<T> ddMin(List<T> failingInput, Function<List<T>, Boolean> test){
        return ddMin(failingInput, test, 2);
    }

    private static <T> List<T> ddMin(List<T> failingInput, Function<List<T>, Boolean> test, int n){
        if (failingInput.size() == 1){
            return failingInput;
        }
        List<T> newInput = removeChunk(getChunks(failingInput, n), test);
        if (newInput.size() > 0){
            return ddMin(newInput, test, Math.max(n-1, 2));
        }
        else if (n < failingInput.size()){
            return ddMin(failingInput, test, Math.min(2*n, failingInput.size()));
        } else {
            return failingInput;
        }
    }

    private static <T> List<T> removeChunk(List<List<T>> chunks, Function<List<T>, Boolean> test){
        int nChunks = chunks.size();
        for (int c = 0; c < nChunks; c++){
            List<T> testData = new ArrayList<>();
            for (int i = 0; i < nChunks; i++){
                if (c != i){
                    testData.addAll(chunks.get(i));
                }
            }
            if (!test.apply(testData)){
                return testData;
            }
        }
        return new ArrayList<>();
    }

    private static <T> List<List<T>> getChunks(List<T> failingInput, int n){
        List<List<T>> chunks = new ArrayList<>();
        int lenChunk = failingInput.size() / n;
        int i = 0;
        List<T> current = new ArrayList<>();
        for (T t : failingInput){
            current.add(t);
            i++;
            if (i >= lenChunk){
                chunks.add(current);
                current = new ArrayList<>();
                i = 0;
            }
        }
        if (current.size() > 0){
            chunks.get(n-1).addAll(current);
        }
        return chunks;
    }
}
