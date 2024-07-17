package com.games.leaderboard.controller;

import com.games.leaderboard.pojos.LeaderBoardRecord;
import com.games.leaderboard.pojos.Player;
import com.games.leaderboard.service.LeaderBoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.StreamUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(LeaderBoardController.class)
public class LeaderBoardControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LeaderBoardService leaderBoardService;

    @Test
    public void testGetLeaderBoardWithParams() throws Exception {

        LeaderBoardRecord leaderBoardRecord1 = new LeaderBoardRecord();
        leaderBoardRecord1.setScore(130);
        leaderBoardRecord1.setPlayerID(1);
        

        LeaderBoardRecord leaderBoardRecord2 = new LeaderBoardRecord();
        leaderBoardRecord2.setScore(230);
        leaderBoardRecord2.setPlayerID(2);
        

        List<LeaderBoardRecord> leaderBoardRecordList = new ArrayList<>();
        leaderBoardRecordList.add(leaderBoardRecord2);
        leaderBoardRecordList.add(leaderBoardRecord1);

        when(leaderBoardService.getLeaderBoard(anyInt(), anyInt(), anyString())).thenReturn(leaderBoardRecordList);

        FileSystemResource resource = new FileSystemResource("D:\\leaderboard\\leaderboard\\src\\test\\resources\\expectedJSONResponseWithParams.json");
        String expectedResponseBody = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);

        mockMvc.perform(MockMvcRequestBuilders.get("/game-portal/leaderboard/getLeaderBoard")
                        .param("pageNumber", "1")
                        .param("batchSize", "2")
                        .param("sortKey", "DESC")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponseBody));
    }

    @Test
    public void testGetLeaderBoardWithoutParams() throws Exception {

        LeaderBoardRecord leaderBoardRecord1 = new LeaderBoardRecord();
        leaderBoardRecord1.setScore(130);
        leaderBoardRecord1.setPlayerID(1);
        

        LeaderBoardRecord leaderBoardRecord2 = new LeaderBoardRecord();
        leaderBoardRecord2.setScore(230);
        leaderBoardRecord2.setPlayerID(2);
        

        LeaderBoardRecord leaderBoardRecord3 = new LeaderBoardRecord();
        leaderBoardRecord3.setScore(30);
        leaderBoardRecord3.setPlayerID(3);

        List<LeaderBoardRecord> leaderBoardRecordList = new ArrayList<>();
        leaderBoardRecordList.add(leaderBoardRecord2);
        leaderBoardRecordList.add(leaderBoardRecord1);
        leaderBoardRecordList.add(leaderBoardRecord3);

        when(leaderBoardService.getLeaderBoard(anyInt(), anyInt(), anyString())).thenReturn(leaderBoardRecordList);

        FileSystemResource resource = new FileSystemResource("D:\\leaderboard\\leaderboard\\src\\test\\resources\\expectedJSONResponseWithoutParams.json");
        String expectedResponseBody = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);


        mockMvc.perform(MockMvcRequestBuilders.get("/game-portal/leaderboard/getLeaderBoard")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponseBody));
    }


    @Test
    public void testGetPlayerProfile() throws Exception {
        Player player = new Player();

        player.setPlayerID(1);
        player.setEmail("xyz@gmail.com");
        player.setName("abc");
        player.setPassword("abcdefghij");
        player.setGameID(1);

        when(leaderBoardService.getPlayerDetails(anyInt())).thenReturn(player);

        mockMvc.perform(MockMvcRequestBuilders.get("/game-portal/leaderboard/viewProfile/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.name").value("abc"));

    }

    @Test
    public void testGetRankAndScore() throws Exception {

        LeaderBoardRecord leaderBoardRecord1 = new LeaderBoardRecord();
        leaderBoardRecord1.setScore(130);
        leaderBoardRecord1.setPlayerID(1);
        

        when(leaderBoardService.getRankAndScore(anyInt())).thenReturn(leaderBoardRecord1);

        mockMvc.perform(MockMvcRequestBuilders.get("/game-portal/leaderboard/viewRankAndScore/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
