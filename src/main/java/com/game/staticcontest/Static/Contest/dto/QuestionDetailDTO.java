package com.game.staticcontest.Static.Contest.dto;

import javax.swing.text.AbstractDocument;
import java.util.List;

public class QuestionDetailDTO {

    private String questionId;
    private String name;
    private ContentDTO contentDTO;
    private List<OptionDTO> optionDTOList;
    private List<OptionDTO> correctDTOList;
    private String category;
    private String mediaType;
    private String ansType;
    private String difficulty;
    private int duration;


}
