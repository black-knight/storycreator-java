import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by daniel on 10/02/14.
 */
public class ChoiceGroup {
    public String name;
    public List<Choice> choiceList = new ArrayList<Choice>();
    private List<ChoiceGroup> mostRecentOtherGroups;
    private List<ChoiceGroup> mostRecentGroups;
    private List<ChoiceGroup> dependsOnGroups;
    private OutputGlue outputGlue;

    public ChoiceGroup(String name) {
        this.name = name;
        outputGlue = new OutputGlue(", ", " samt ", ". ");
    }

    public void setMostRecentOtherGroups(List<ChoiceGroup> mostRecentOtherGroups) {
        this.mostRecentOtherGroups = mostRecentOtherGroups;
    }

    public List<ChoiceGroup> getMostRecentOtherGroups() {
        return mostRecentOtherGroups;
    }

    public void setMostRecentGroups(List<ChoiceGroup> mostRecentGroups) {
        this.mostRecentGroups = mostRecentGroups;
    }

    public List<ChoiceGroup> getMostRecentGroups() {
        return mostRecentGroups;
    }

    public void setDependsOnGroups(List<ChoiceGroup> dependsOnGroups) {
        this.dependsOnGroups = dependsOnGroups;
    }

    public List<ChoiceGroup> getDependsOnGroups() {
        return dependsOnGroups;
    }

    public boolean isApplicable(List<ChoiceGroup> choiceGroupHistory) {
        return (verifyDependsOnGroups(choiceGroupHistory) &&
                verifyMostRecentGroups(choiceGroupHistory) &&
                verifyMostRecentOtherGroups(choiceGroupHistory));
    }

    private boolean verifyMostRecentOtherGroups(List<ChoiceGroup> choiceGroupHistory) {
        if (mostRecentOtherGroups != null) {
            List<ChoiceGroup> choiceGroupHistoryOther = new LinkedList<ChoiceGroup>();
            choiceGroupHistoryOther.addAll(choiceGroupHistory);
            choiceGroupHistoryOther.remove(this);
            if (mostRecentOtherGroups.size() > choiceGroupHistoryOther.size()) {
                return false;
            }
            for (int i = 0; i < mostRecentOtherGroups.size(); i++) {
                if (mostRecentOtherGroups.get(mostRecentOtherGroups.size() - 1 - i) != choiceGroupHistoryOther.get(choiceGroupHistoryOther.size() - 1 - i)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean verifyMostRecentGroups(List<ChoiceGroup> choiceGroupHistory) {
        if (mostRecentGroups != null) {
            if (mostRecentGroups.size() > choiceGroupHistory.size()) {
                return false;
            }
            for (int i = 0; i < mostRecentGroups.size(); i++) {
                if (mostRecentGroups.get(mostRecentGroups.size() - 1 - i) != choiceGroupHistory.get(choiceGroupHistory.size() - 1 - i)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean verifyDependsOnGroups(List<ChoiceGroup> choiceGroupHistory) {
        if (dependsOnGroups != null) {
            for (ChoiceGroup group: dependsOnGroups) {
                if (!choiceGroupHistory.contains(group)) {
                    return false;
                }
            }
        }
        return true;
    }

    public void setOutputGlue(OutputGlue outputGlue) {
        if (outputGlue != null) {
            this.outputGlue = outputGlue;
        }
    }

    public OutputGlue getOutputGlue() {
        return outputGlue;
    }
}
