$(document).read(function() {
	const genPassword = $('genPassword');
	const copyTextBtn = $('copyTextBtn');
	
	$copyTextBtn.on('click', function() {
		const text = genPassword.text();
		
		if(!navigator.clipboard) {
			alert('このブラウザではクリップボードコピーに対応していません。')
			return;
		}
		
		navigator.clipboard.writeText(text)
			.then(function() {
				alert('テキストをコピーしました。')
			})
			.catch(function() {
				console.error('クリップボードへのコピーに失敗しました:', err);
				alert('クリップボードへのコピーに失敗しました。')
			})
	})
})