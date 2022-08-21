package duke.command;

import java.time.LocalDateTime;

import duke.DukeException;
import duke.Output;
import duke.Parser;
import duke.StorageList;
import duke.Ui;

public class MatchCommand extends Command {
    @Override
    public void execute(Ui ui, StorageList storageList) throws DukeException {
        String input = ui.getLastInput();
        String command = ui.getLastCommand();
        Output.DATE.listMatches(storageList,
                LocalDateTime.parse(Parser.findFirstCommand(input, command), Ui.getInputFormatter()));
    }
}
