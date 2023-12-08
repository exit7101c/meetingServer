(function() {"use strict";

    var movingAvgCalculator = {
        init: MovingAvgCalculator
    };

    /**
     * D3 이동평균 차트영역
     *
     * @param {string} selector - css selector
     * @param {string} $target - 하이차트 참조하는 jQuery element. Width 맞출 때 사용함.
     * @param {Array} data - 차트 데이타. Empty 배열 넣도 됨.
     * @param {number} maSampleCount - 이동평균 sample 갯수
     * @param {number} sampleRange - 판정할 샘플 수
     * @param {number} judgeSampleCount - 이동평균 증가/하락 추세 기준
     * @Return {object} 이동평균차트 object
     * */
    function MovingAvgCalculator(data, maSampleCount, sampleRange, judgeSampleCount){

        // "new" 예약어 제거
        if(!(this instanceof MovingAvgCalculator)) {
            return new MovingAvgCalculator(data, maSampleCount, sampleRange, judgeSampleCount);
        }

        // 차트 instance 변수 초기화 및 선언
        this.chartData              = data || [];                   // 차트 데이타
        this.movingAvg              = [];                           // 차트 이동평균데이타
        this.movingAvgDiff          = [];                           // 차트 이동평균차이 데이타
        this.currentMovingAvgDiff   = 0;                            // 현재 이동 평균차이
        this.maSum                  = 0;                            // 중량합계
        this.maSampleCount          = maSampleCount || 100;         // 이동평균 sample count
        // 판정관련된변수
        this.sampleRange            = sampleRange || 5;             // 판정할 샘플 수
        this.judgeSampleCount       = judgeSampleCount || 4;        // 이동평균 증가/하락 추세 기준
    }
    /**
     * 여기서 'this' 는 'MovingAvgCalculator' 객채다
     * @return {number} 표준편차 차이
     * */
    var fnGenerateMovingAvgDiff = function(firstVal, secondVal){
        secondVal = secondVal || firstVal;     // secondVal 이 없으면 0이 나와야되니까.
        var sum = firstVal - secondVal;
        this.movingAvgDiff.push(sum);
        return firstVal - secondVal;
    };

    /**
     * 현재 이동평균차이값을 가져오기
     * @return {number} 현재 이동평균차이값
     * */
    MovingAvgCalculator.prototype.getCurrentMovingAverageDiff = function() {
        return this.currentMovingAvgDiff;
    };
    
    /**
     * 현재 이동평균값 가져오기
     * @return {number} 현재 이동평균값
     * */
    MovingAvgCalculator.prototype.getCurrentMovingAverage = function() {
        var ma = this.movingAvg;
        return ma[ma.length - 1];
    };
    
    /**
     * 차트 데이타/element 초기화
     * @Return {object} chart element
     * */
    MovingAvgCalculator.prototype.reset = function() {
        this.chartData              = [];       // 차트 데이타
        this.movingAvg              = [];       // 차트 이동평균데이타
        this.movingAvgDiff          = [];       // 차트 이동평균차이 데이타
        this.currentMovingAvgDiff   = 0;        // 현재 이동 평균차이
        this.maSum                  = 0;        // 중량합계
        return this;
    };

    /**
     * 이동평균값 결과와 계산할 때 필요한 값들 결과로 그린다
     * @return {object} this
     * */
    MovingAvgCalculator.prototype.addMovingAvg = function(){
        var
            dataArr         = this.chartData,
            dataLen         = dataArr.length,
            movingAvg       = 0;                    // 이동평균값.

        if (dataLen === 0) {
            return;     // Do nothing if there is no data.
        }

        // 현재 중량값을 해당 차트 sum 에 추가
        var curVal = dataArr[dataLen - 1][1];
        this.maSum += curVal;
        movingAvg = Number((this.maSum / dataLen).toFixed(1));

        // 배열에 이동평균 데이타 추가
        this.movingAvg.push(+movingAvg.toFixed(1));

        // 배열에 이동평균 차이 데이타 추가. -2는 이전값 가져욀 때 쓰기 위해서
        var maDifference = fnGenerateMovingAvgDiff.call(this, movingAvg, this.movingAvg[dataLen - 2]);

        // 현재 이동평균차이 세팅
        this.currentMovingAvgDiff = maDifference;

        return this;
    };

    /**
     * D3 이동평균 차트에 Data append 하기
     * */
    MovingAvgCalculator.prototype.addData = function (pushedData, isShift){

        var data        = this.chartData;
        data.push(pushedData);

        // Data shift 하기
        if (data.length > this.maSampleCount) {
            // 중량 데이타 shift
            var firstData = data.shift();
            // 합계 조정
            this.maSum -= firstData[1];
            // 이동평균 및 이동평균차이 배열 shift
            this.movingAvg.shift();
            this.movingAvgDiff.shift();
        }

        // Add moving avg Data.
        this.addMovingAvg();

    };

    //Exposure to global window. Improve later if needed
    window.movingAvgCalculator = movingAvgCalculator;



})();
