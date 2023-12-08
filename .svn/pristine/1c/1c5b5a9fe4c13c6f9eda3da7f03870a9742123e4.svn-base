package com.nse.pms.standard.ctq.controller;

import com.nse.pms.standard.ctq.service.NseCtqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "ctq")
public class NseCtqController {

	@Autowired
	private NseCtqService NseCtqService;

	@RequestMapping(value = "selectCtqTreeList", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<HashMap<String, Object>> selectCtqTreeList(@RequestParam HashMap<String, Object> param, ModelMap model)
			throws Exception {
		return NseCtqService.selectCtqTreeList(param);
	}

	@RequestMapping(value = "selectCtqTreeListUseCtrl", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<HashMap<String, Object>> selectCtqTreeListUseCtrl(@RequestParam HashMap<String, Object> param,
			ModelMap model) throws Exception {
		return NseCtqService.selectCtqTreeListUseCtrl(param);
	}

	@RequestMapping(value = "selectCtqCtrlChart", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<HashMap<String, Object>> selectCtqCtrlChart(@RequestParam HashMap<String, Object> param, ModelMap model)
			throws Exception {
		return NseCtqService.selectCtqCtrlChart(param);
	}

	@RequestMapping(value = "selectCtqTrendChart", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<HashMap<String, Object>> selectCtqTrendChart(@RequestParam HashMap<String, Object> param,
			ModelMap model) throws Exception {
		return NseCtqService.selectCtqTrendChart(param);
	}

	@RequestMapping(value = "selectCtqTrendChart2", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<HashMap<String, Object>> selectCtqTrendChart2(@RequestParam HashMap<String, Object> param,
															 ModelMap model) throws Exception {
		return NseCtqService.selectCtqTrendChart2(param);
	}

	//상관분석 접점데이터 조회
    @RequestMapping(value = "selectCorrelTagDataList", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public List<HashMap<String, Object>> selectCorrelTagDataList(@RequestParam HashMap<String, Object> param,
                                                             ModelMap model) throws Exception {
        return NseCtqService.selectCorrelTagDataList(param);
    }

	@RequestMapping(value = "selectCtqHistoryList", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<HashMap<String, Object>> selectCtqHistoryList(@RequestParam HashMap<String, Object> param,ModelMap model) throws Exception {
		return NseCtqService.selectCtqHistoryList(param);
	}
	
	@RequestMapping(value = "selectCtqComparisonList", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<HashMap<String, Object>> selectCtqComparisonList(@RequestParam HashMap<String, Object> param,ModelMap model) throws Exception {
		return NseCtqService.selectCtqComparisonList(param);
	}

	/**
	 * [CTQ간 비교조회] 차트데이터 조회
	 **/
	@RequestMapping(value = "selectCtqMultiChart", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<HashMap<String, Object>> selectCtqMultiChart(@RequestParam HashMap<String, Object> param,ModelMap model) throws Exception {
		return NseCtqService.selectCtqMultiChart(param);
	}
	
	/**
	 * TagCd 의 시간별/측정별/월별 등 Value List
	 **/
	@RequestMapping(value = "selectCtqValueList", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<HashMap<String, Object>> selectCtqValueList(@RequestParam HashMap<String, Object> param,ModelMap model) throws Exception {
		return NseCtqService.selectCtqValueList(param);
	}

	/**
	 * CTQ 즐겨찾기 추가
	 **/
	@RequestMapping(value = "insertCtqFavorite", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public HashMap<String, Object> insertCtqFavorite(@RequestParam HashMap<String, Object> param,ModelMap model) throws Exception {
		return NseCtqService.insertCtqFavorite(param);
	}


	/**
	 * CTQ 컨티션 모니터링 추가
	 **/
	@RequestMapping(value = "insertCtqMoniCond", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public HashMap<String, Object> insertCtqMoniCond(@RequestParam HashMap<String, Object> param,ModelMap model) throws Exception {
		return NseCtqService.insertCtqMoniCond(param);
	}

	/**
	 * CTQ 즐겨찾기 조회
	 **/
	@RequestMapping(value = "selectCtqFavorite", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<HashMap<String, Object>> selectCtqFavorite(@RequestParam HashMap<String, Object> param,ModelMap model) throws Exception {
		return NseCtqService.selectCtqFavorite(param);
	}

	/**
	 * CTQ 컨티션 모니터링 조회
	 **/
	@RequestMapping(value = "selectCtqMoniCond", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<HashMap<String, Object>> selectCtqMoniCond(@RequestParam HashMap<String, Object> param,ModelMap model) throws Exception {
		return NseCtqService.selectCtqMoniCond(param);
	}
	
	/**
	 * 시간별 CTQ 비교조회 
	 **/
	@RequestMapping(value = "selectCtqCompareList", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<HashMap<String, Object>> selectCtqCompareList(@RequestParam HashMap<String, Object> param,ModelMap model) throws Exception {
		return NseCtqService.selectCtqCompareList(param);
	}

	/**
	 * 접점 정보조회
	 **/
	@RequestMapping(value = "selectTagInfo", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public HashMap<String, Object> selectTagInfo(@RequestParam HashMap<String, Object> param,ModelMap model) throws Exception {
		return NseCtqService.selectTagInfo(param);
	}

	/**
	 * CTQ 상관분석 데이터 삭제
	 **/
	@RequestMapping(value = "deleteCtqMultipleCorrelate", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public HashMap<String, Object> deleteCtqMultipleCorrelate(@RequestParam HashMap<String, Object> param,ModelMap model) throws Exception {
		return NseCtqService.deleteCtqMultipleCorrelate(param);
	}

    /**
     * CTQ 상관분석 SEQ 조회
     **/
	@RequestMapping(value = "selectCorrelDataIdx", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public List<HashMap<String, Object>> selectCorrelDataIdx(@RequestParam HashMap<String, Object> param, ModelMap model)
            throws Exception {
        return NseCtqService.selectCorrelDataIdx(param);
    }

	/**
	 * CTQ 상관분석 등록
	 */
	@RequestMapping( value="insertCtqMultipleCorrelate", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> insertCtqMultipleCorrelate(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return NseCtqService.insertCtqMultipleCorrelate(param);
	}


    /**
     * CTQ 상관분석 차트 모달 데이터조회
     */
    @RequestMapping(value = "selectCorrelateChartList", method = { RequestMethod.GET, RequestMethod.POST })
    public List<HashMap<String, Object>> selectCorrelateChartList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
        return NseCtqService.selectCorrelateChartList(param);
    }

	/*월별 주 리스트*/
	@RequestMapping(value = "selectWeekList", method = { RequestMethod.GET, RequestMethod.POST })
	public List<HashMap<String, Object>> selectWeekList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return NseCtqService.selectWeekList(param);
	}

}