$(document).ready(function() {
	
	var inputNumber = $('#inputNumber');
	var unitSelect = $('#unitSelect');
	var unitDetailBody = $('#unitDetailBody');
	var unitSummary = appData.unitSummary; 
	
	// 単位が入力されたとき、単位表示テーブルの内容を変更する
	unitSelect.change(function() {
		const numberStr = inputNumber.val();
		const number = parseFloat(numberStr);
		const selectUnitId = parseInt(unitSelect.val());
		
		// 数字が入力されていないときは処理をしない
		// 単位が選択されていないときは処理をしない
		if(selectUnitId === 0 || number === 0) {
			
			// テーブルの内容をクリア
			unitDetailBody.empty(); 
		
		} else {
			if(!isNaN(number)) {
				
				// テーブルの内容をクリア
				unitDetailBody.empty(); 
				
				$.each(unitSummary, function(index, unit) {
					
					if(selectUnitId === unit.unitId) {
						$.each(unit.unitDetailList, function(index, unitDetail) {
							var calculatedValue = number / unitDetail.unitDetailValue;
							var tempValue = calculatedValue.toFixed(15);
							var formattedValue = tempValue.replace(/\.?0+$/, '');
							var row = '<tr>' +
										'<td>' + formattedValue + '</td>' +
										'<td>' + unitDetail.unitDetailName + '</td>' +
										'</tr>';
										unitDetailBody.append(row);
						});
		
						return false;
					}
					
				});
			}
		}
	});
	
	// 数字が入力されたとき、単位表示テーブルの内容を変更する
	inputNumber.on('input', function() {
		unitSelect.change();
	});
	
	// ページ初期ロード時も実施
	unitSelect.change();
});