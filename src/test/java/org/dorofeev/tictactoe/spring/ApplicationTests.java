package org.dorofeev.tictactoe.spring;

import org.dorofeev.tictactoe.spring.model.GameResponse;
import org.dorofeev.tictactoe.spring.model.GameStatus;
import org.dorofeev.tictactoe.spring.model.MakeMoveRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testStartNewGame() {
        String response = restTemplate.postForObject("/startNewGame", null, String.class);
        assertThat(response).isNull();
    }

    @Test
    public void testMakeNewMoveWithPositionPostRequestOk() {
        restTemplate.postForObject("/startNewGame", null, String.class);

        MakeMoveRequest request = new MakeMoveRequest();
        request.setFigure("X");
        request.setPosition("0");

        GameResponse response = restTemplate.postForObject("/makeNewMoveWithPosition", request, GameResponse.class);
        assertThat(response)
                .extracting("status")
                .contains(GameStatus.CONTINUE.name());
    }

    @Test
    public void testMakeNewMoveWithPositionPostRequestError() {
        MakeMoveRequest request = null;

        String response = restTemplate.postForObject("/makeNewMoveWithPosition", request, String.class);
        assertThat(response)
                .contains("Unsupported Media Type")
                .contains("415")
                .contains("error");
        request = new MakeMoveRequest();
        response = restTemplate.postForObject("/makeNewMoveWithPosition", request, String.class);
        assertThat(response)
                .contains("Internal Server Error")
                .contains("500")
                .contains("figure parameter is empty")
                .contains("error");
    }

    @Test
    public void testMakeNewMovePostRequestOk() {
        restTemplate.postForObject("/startNewGame", null, String.class);

        MakeMoveRequest request = new MakeMoveRequest();
        request.setFigure("O");

        GameResponse response = restTemplate.postForObject("/makeNewMove", request, GameResponse.class);
        assertThat(response)
                .extracting("status")
                .contains(GameStatus.CONTINUE.name());
        assertThat(response)
                .extracting("position")
                .contains("0");
    }

    @Test
    public void testMakeSmart() {
        String response = restTemplate.postForObject("/makeComputerSmart", null, String.class);
        assertThat(response).isNull();
    }

}