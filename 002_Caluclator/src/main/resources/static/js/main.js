$(document).ready(function() {
	var display = $('#display');
    var numberKey = $('.key-number');
	var markKey = $('.key-mark');
	var saveNumber = $('save-number');
	var saveMark = $('save-mark');

	// 押下されたナンバーキーをディスプレイに追記する
	numberKey.on('click', function() {
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
		} else if(selectkey == "=") {
			console.log("計算結果");
		} else {
			if($.isNumeric(display.text().slice(-1))) {
				display.text(display.text() + selectkey);
			} else if(display.text() != "") {
				display.text(display.text().slice(0, -1) + selectkey);
			}
		}
	});

	
	
});