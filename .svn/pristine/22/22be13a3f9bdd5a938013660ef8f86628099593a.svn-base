package cronies.meeting.user.controller;

import cronies.meeting.user.service.CommonService;
import cronies.meeting.user.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import select.spring.exquery.service.ExqueryService;

import java.util.HashMap;

@RestController
@RequestMapping(value = "reservation")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ExqueryService exqueryService;

    @Autowired
    private CommonService commonService;

    @RequestMapping(value = "getIsReservation", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public HashMap<String, Object> getIsReservation(@RequestParam HashMap<String, Object> param) throws Exception {
        return reservationService.getIsReservation(param);
    }

    @RequestMapping(value = "setPreReservation", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public HashMap<String, Object> setPreReservation(@RequestParam HashMap<String, Object> param) throws Exception {
        return reservationService.setPreReservation(param);
    }

    @RequestMapping(value = "getIsReservation2", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public HashMap<String, Object> getIsReservation2(@RequestParam HashMap<String, Object> param) throws Exception {
        return reservationService.getIsReservation2(param);
    }

    @RequestMapping(value = "getInvitePreReservationCnt", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public HashMap<String, Object> getInvitePreReservationCnt(@RequestParam HashMap<String, Object> param) throws Exception {
        return reservationService.getInvitePreReservationCnt(param);
    }

    @RequestMapping(value = "selectPreReservationCnt", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public HashMap<String, Object> selectPreReservationCnt(@RequestParam HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = exqueryService.selectOne("cronies.app.setting.selectPreReservationCnt", param);
        return resultMap;
    }


}
