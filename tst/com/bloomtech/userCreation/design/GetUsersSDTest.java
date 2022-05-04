package com.bloomtech.userCreation.design;

import com.amazon.ata.test.assertions.PlantUmlSequenceDiagramAssertions;
import com.amazon.ata.test.helper.AtaTestHelper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class GetUsersSDTest {
    private static final String GET_USER_SEQUENCE_DIAGRAM_PATH = "get-user-SD.puml";

    @ParameterizedTest
    @ValueSource(strings = {"UserController", "UserService", "UserDataRepository"})
    void getUserSequenceDiagram_includesExpectedTypes(String type) {
        String content = AtaTestHelper.getFileContentFromResources(GET_USER_SEQUENCE_DIAGRAM_PATH);

        PlantUmlSequenceDiagramAssertions.assertSequenceDiagramContainsEntity(content, type);
    }

    @ParameterizedTest
    @ValueSource(strings = {"List<User>", "200 Response"})
    void getUserSequenceDiagram_includesExpectedReturnTypes(String type) {
        String content = AtaTestHelper.getFileContentFromResources(GET_USER_SEQUENCE_DIAGRAM_PATH);

        PlantUmlSequenceDiagramAssertions.assertSequenceDiagramContainsReturnType(content, type);
    }

    @ParameterizedTest
    @ValueSource(strings = {"getUsersList"})
    void getUserSequenceDiagram_includesExpectedMethodCalls(String method) {
        String content = AtaTestHelper.getFileContentFromResources(GET_USER_SEQUENCE_DIAGRAM_PATH);

        PlantUmlSequenceDiagramAssertions.assertSequenceDiagramContainsMethod(content, method);
    }
}
