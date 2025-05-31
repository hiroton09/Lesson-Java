$(document).ready(function() {
	
	// ===== 定数 =====
	const TIMER_UPDATE_TIME = 1000;
	const SW_UPDATE_TIME = 10;
	const N_MSG_0001 = "時間になりました";
	const E_MSG_0001 = "タイマーが設定されていません。\n時間を設定してください。";
	
	// ===== 変数 =====
	var timerTime = 0;
	var timerInterval;
	var timerDisplayTextHH = $('#timerDisplayTextHH');
	var timerDisplayTextMM = $('#timerDisplayTextMM');
	var timerDisplayTextSS = $('#timerDisplayTextSS');
	var minute1Btn = $('#minute1Btn');
	var minute5Btn = $('#minute5Btn');
	var minute10Btn = $('#minute10Btn');
	var timerStartBtn = $('#timerStartBtn');
	var timerStopBtn = $('#timerStopBtn');
	var timerResetBtn = $('#timerResetBtn');
	var swTime = 0;
	var swInterval;
	var swDisplayText = $('#swDisplayText');
	var swStartBtn = $('#swStartBtn');
	var swStopBtn = $('#swStopBtn');
	var swResetBtn = $('#swResetBtn');
	
	// ===== タイマー機能 =====
	// 開始
	timerStartBtn.on('click', function() {
		
		// 入力された時間を取得
		const hh = parseInt(timerDisplayTextHH.val() || 0);
		const mm = parseInt(timerDisplayTextMM.val() || 0);
		const ss = parseInt(timerDisplayTextSS.val() || 0);

		timerTime = hh * 3600 + mm * 60 + ss;
		
		// タイマーが設定されているか判定
		if(timerTime <= 0) {
			alert(E_MSG_0001);
			return;
		}
		
		// タイマーが既に動作しているか判定
		if (timerInterval) {
			return;
		}

		// タイマー作動中は開始ボタン、リセットボタン、各分ボタンは非活性
		// 入力欄を入力不可
		setDisableBtn(true);

		// タイマー作動処理
		timerInterval = setInterval(function() {
			
			timerTime--;
			setTimerDisplay(timerTime);
			
			// タイマーが0になったらアラート表示
			if (timerTime <= 0) {
				clearInterval(timerInterval);
				timerInterval = null;
				
				// 完了メッセージ
				alert(N_MSG_0001);

				// リセット
				setTimerDisplay(0);
				
				// タイマー作動中は開始ボタン、リセットボタン、各分ボタンは活性
				// 入力欄を入力可
				setDisableBtn(false);			
			}
		}, TIMER_UPDATE_TIME);
	});
	
	// 停止
	timerStopBtn.on('click', function() {
		clearInterval(timerInterval);
		timerInterval = null;
		
		// タイマー作動中は開始ボタン、リセットボタン、各分ボタンは活性
		// 入力欄を入力可
		setDisableBtn(false);
	});
	
	// リセット
	timerResetBtn.on('click', function() {
		setTimerDisplay(0);
		
		// タイマー作動中は開始ボタン、リセットボタン、各分ボタンは活性
		// 入力欄を入力可
		setDisableBtn(false);
	})
	
	// 1分セットボタン
	minute1Btn.on('click', function() {
		setTimerDisplay(60);
	})
	
	// 5分セットボタン
	minute5Btn.on('click', function() {
		setTimerDisplay(300);
	})
	
	// 10分セットボタン
	minute10Btn.on('click', function() {
		setTimerDisplay(600);
	})
	
	// ===== ストップウォッチ機能 =====
	// 開始
	swStartBtn.on('click', function() {
		// タイマーが既に動作しているか判定
		if (swInterval) {
			return;
		}
		swInterval = setInterval(function() {
			swTime++;
			swDisplayText.text(formatTime(swTime));
		}, SW_UPDATE_TIME);
	});
	
	// 停止
	swStopBtn.on('click', function() {
		clearInterval(swInterval);
		swInterval = null;
	});
	
	// リセット
	swResetBtn.on('click', function() {
		swTime = 0;
		swDisplayText.text(formatTime(swTime));
	})
	
	// ===== フォーマット処理 =====
	function formatTime(time) {
		const hh = Math.floor(time/36000);
		time %= 36000;
		const mm = Math.floor(time/6000);
		time %= 6000;
		const ss = Math.floor(time/100);
		const ff = time % 100;
		
		
		return `${pad(hh, 2)}:${pad(mm, 2)}:${pad(ss, 2)}:${pad(ff, 2)}`;
	}
	
	// それぞれの時間を2桁にフォーマット
	function pad(num, length) {
		return num.toString().padStart(length, '0');
	}
	
	// ===== ボタン活性化処理 =====
	function setDisableBtn(isDisable) {
		
		minute1Btn.prop('disabled', isDisable);
		minute5Btn.prop('disabled', isDisable);
		minute10Btn.prop('disabled', isDisable);
		timerStartBtn.prop('disabled', isDisable);
		timerResetBtn.prop('disabled', isDisable);
		
		timerDisplayTextHH.prop('disabled', isDisable);
		timerDisplayTextMM.prop('disabled', isDisable);
		timerDisplayTextSS.prop('disabled', isDisable);
	}
	
	// ===== 時間設定 =====
	function setTimerDisplay(time) {
		timerDisplayTextHH.val(pad(Math.floor(time / 3600), 2));
		timerDisplayTextMM.val(pad(Math.floor(time / 60), 2));
		timerDisplayTextSS.val(pad(time % 60, 2));
	}
});