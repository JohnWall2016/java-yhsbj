package yhsbj.util;

public class Paths {

    public static String AppendToFileName(String fileName, String append) {
        var idx = fileName.lastIndexOf('.');
        var newFile = "";
        if (idx > 0) {
            newFile = fileName.substring(0, idx);
            newFile += append;
            newFile += fileName.substring(idx);
        } else {
            newFile = fileName + append;
        }
        return newFile;
    }

}
