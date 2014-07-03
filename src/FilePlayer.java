import java.io.File;

public abstract class FilePlayer implements Player {
    private final File fileName;

    public FilePlayer(File fileName) {
        this.fileName = fileName;
    }
}
