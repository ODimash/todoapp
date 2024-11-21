package odimash.todoapp.model;

public class ImportantTask extends Task {
    public ImportantTask(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[IMPORTANT] " + description;
    }
}
