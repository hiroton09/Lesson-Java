$(document).ready(function(){
	
	const E_MSG_0001 = "生成する文字列を入力してください。";
	
	var inputText = $('#inputText');
	var genQrBtn = $('#genQrBtn');
	var qrDisplay = $('#qrDisplay');
	var downloadBtn = $('#downloadBtn');
	
	var qrcodeInstance = null;
	
	// 初期状態ではダウンロードボタンを非表示にする
    downloadBtn.hide();
	
	// QRコード生成ボタン
	genQrBtn.on('click', function(){
		const text = inputText.val().trim();
		
		// テキストが入力されているか判定
		if(text) {
			
			// QRコードクリア
			qrDisplay.empty();
			
			// QRコード生成
			qrcodeInstance = new QRCode(qrDisplay[0], {
				text: text,
				width: 256,
				height: 256,
				colorDark: "#000000",
				colorLight: "#ffffff",
				correctLevel: QRCode.CorrectLevel.H
			});
			
			// 初期状態ではダウンロードボタンを非表示にする
		    downloadBtn.show();
			
			const canvas = qrDisplay.find('canvas')[0]
			if(canvas) {
				downloadBtn.attr('href', canvas.toDataURL('image/png'));
			}
			
		} else {
			alert(E_MSG_0001);
			downloadBtn.hide();
		}
	});
});