package com.cronies.smartmes.controller;

import com.cronies.smartmes.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "board")
public class BoardController {

	@Autowired
	private BoardService boardService;

	@RequestMapping(value = "getBoardList", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<HashMap<String, Object>> getBoardList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return boardService.getBoardList(param);
	}

	@RequestMapping(value = "boardDatList", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<HashMap<String, Object>> boardDatList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return boardService.boardDatList(param);
	}

	@RequestMapping( value="selectMesBoardOne", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> selectMesBoardOne(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return boardService.selectMesBoardOne(param);
	}

	@RequestMapping( value="insertBoard", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> insertBoard(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return boardService.insertBoard(param);
	}

	@RequestMapping( value="adminBoardInput", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> adminBoardInput(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return boardService.adminBoardInput(param);
	}

	@RequestMapping( value="boardUpdate", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> boardUpdate(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return boardService.boardUpdate(param);
	}

	@RequestMapping( value="boardDelete", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> boardDelete(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return boardService.boardDelete(param);
	}

	@RequestMapping( value="boardDat", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> boardDat(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return boardService.boardDat(param);
	}



}