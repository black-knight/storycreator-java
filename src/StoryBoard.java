import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by daniel on 09/02/14.
 */
public class StoryBoard {
    public static HashMap<String, StoryObject> storyObjects;
    public static List<Choice> storyChoice;
    public static HashMap<String, ChoiceGroup> storyChoiceGroups;

    public static void init() {
        createStoryboard();
    }

    private static void createStoryboard() {
        JSONObject json = loadJsonStructure();
        createStoryObjects(json);
        createStoryChoice(json);
    }

    private static void createStoryObjects(JSONObject json) {
        storyObjects = new HashMap<String, StoryObject>();

        JSONArray jsonObjects = json.getJSONArray("objects");
        for (int i = 0; i < jsonObjects.length(); i++) {
            JSONObject jsonObject = jsonObjects.getJSONObject(i);
            String name = jsonObject.getString("name");
            String description = jsonObject.getString("description");
            storyObjects.put(name, new StoryObject(name, description));
        }
    }

    private static void createStoryChoice(JSONObject json) {
        storyChoice = new ArrayList<Choice>();
        storyChoiceGroups = new HashMap<String, ChoiceGroup>();

        JSONArray choiceGroups = json.getJSONArray("choiceGroups");
        for (int i = 0; i < choiceGroups.length(); i++) {
            ChoiceGroup choiceGroup = parseChoiceGroup(choiceGroups.getJSONObject(i));
            storyChoiceGroups.put(choiceGroup.name, choiceGroup);
            storyChoice.addAll(choiceGroup.choiceList);
        }
    }

    private static ChoiceGroup parseChoiceGroup(JSONObject jsonObject) {
        ChoiceGroup choiceGroup = new ChoiceGroup(jsonObject.getString("name"));
        choiceGroup.setDependsOnGroups(choiceGroupNamesFromJsonArray(jsonObject.getJSONArray("dependsOnGroups")));
        choiceGroup.setMostRecentGroups(choiceGroupNamesFromJsonArray(jsonObject.getJSONArray("mostRecentGroups")));
        choiceGroup.setMostRecentOtherGroups(choiceGroupNamesFromJsonArray(jsonObject.getJSONArray("mostRecentOtherGroups")));
        JSONArray choiceGroupList = jsonObject.getJSONArray("choices");
        for (int i = 0; i < choiceGroupList.length(); i++) {
            Choice choice = parseChoice(choiceGroupList.getJSONObject(i));
            choiceGroup.choiceList.add(choice);
            choice.setParentChoiceGroup(choiceGroup);
        }
        return choiceGroup;
    }

    private static Choice parseChoice(JSONObject jsonObject) {
        Choice choice = new Choice();
        choice.setSourceObject(storyObjectNamed(jsonObject.getString("object")));
        choice.setTargetObject(storyObjectNamed(jsonObject.getString("targetObject")));
        choice.setOutputText(jsonObject.getString("output"));
        choice.setDependsOnObjects(storyObjectNamesFromJsonArray(jsonObject.getJSONArray("dependsOnObjects")));
        choice.setMostRecentGroups(choiceGroupNamesFromJsonArray(jsonObject.getJSONArray("mostRecentGroups")));
        choice.setOutputGlue(outputGlueFromJsonObject(jsonObject.getJSONObject("outputGlue")));
        return choice;
    }

    private static OutputGlue outputGlueFromJsonObject(JSONObject jsonObject) {
        if (jsonObject == null) {
            return null;
        }
        return new OutputGlue(jsonObject.getString("middleGlue"), jsonObject.getString("lastGlue"), jsonObject.getString("sentenceEnd"));
    }

    private static HashSet<StoryObject> storyObjectNamesFromJsonArray(JSONArray jsonArray) {
        if (jsonArray == null) {
            return null;
        }
        HashSet<StoryObject> storyObjectsSet = new HashSet<StoryObject>();
        for (int i = 0; i < jsonArray.length(); i++) {
            storyObjectsSet.add(storyObjectNamed(jsonArray.getString(i)));
        }
        return storyObjectsSet;
    }

    private static List<ChoiceGroup> choiceGroupNamesFromJsonArray(JSONArray jsonArray) {
        if (jsonArray == null) {
            return null;
        }
        List<ChoiceGroup> choiceGroups = new ArrayList<ChoiceGroup>();
        for (int i = 0; i < jsonArray.length(); i++) {
            choiceGroups.add(choiceGroupNamed(jsonArray.getString(i)));
        }
        return choiceGroups;
    }

    private static ChoiceGroup choiceGroupNamed(String name) {
        return storyChoiceGroups.get(name);
    }

    private static StoryObject storyObjectNamed(String name) {
        return storyObjects.get(name);
    }

    private static JSONObject loadJsonStructure() {
        return new JSONObject(Util.readTextFile("storyboard.json"));
    }
}
