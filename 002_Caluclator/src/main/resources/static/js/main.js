$(document).ready(function() {
	var display = $('#display');
    var numberKey = $('.key-number');
	var markKey = $('.key-mark');
	var saveNumber = $('save-number');
	var saveMark = $('save-mark');
	var afterNumberFlg = false;

	// 押下されたナンバーキーをディスプレイに追記する
	numberKey.on('click', function() {
		if(afterNumberFlg) {
			display.text("");
			afterNumberFlg = false;
		}
        display.text(display.text() + $(this).children('p').text());
    });

	// 押下された記号キーにより処理を分岐
	markKey.on('click', function() {
		var selectkey = $(this).children('p').text();

		// Cが押下された場合、ディスプレイを空にする	
		// =が押下された場合、
		// 		saveNumberとsaveMarkに値がない場合、なにもしない
		// 		saveNumberとsaveMarkに値があり、ディスプレイに数字がない場合、saveNumberを表示
		// 		saveNumberとsaveMarkに値があり、ディスプレイに数字がある場合、計算した結果を表示する
		// 小数点が押下された場合、複数入力されていないかのチェックを行う
		// 四則演算記号が押下された場合
		// 		ディスプレイの文字が空の場合、なにもしない
		// 		saveMarkに値が既に入っている場合、saveNumberはそのままでsaveMarkのみ入れ替える
		// 		現在の値と記号を一時保持し、次に入力される値と計算を行う
		// 		割り算ができない場合、ERRの表示を出力
		if(selectkey == "C") {
			display.text("");
			saveNumber = "";
			saveMark = "";
		} else if(selectkey == "=") {
			if(saveNumber.length && saveMark.length && !display.text()) {
				display.text(saveNumber);
			} else if(saveNumber.length && saveMark.length && display.text().length && $.isNumeric(display[0].textContent)) {
				console.log("計算可能：");
				switch(saveMark) {
					case "+":
						display.text(Number(saveNumber) + Number(display[0].textContent));
						break;
					case "-":
						display.text(Number(saveNumber) - Number(display[0].textContent));
						break;
					case "*":
						display.text(Number(saveNumber) * Number(display[0].textContent));
						break;
					case "/":
						display.text(Number(saveNumber) / Number(display[0].textContent));
						break;					
				}
				saveNumber = display[0].textContent;
				saveMark = "";
			}
		} else {
			if(!saveMark.length) {
				saveNumber = display[0].textContent;
				display.text("");
			}
			saveMark = selectkey;
			display.text(selectkey);
			afterNumberFlg = true;
		}
	});

	
	
});