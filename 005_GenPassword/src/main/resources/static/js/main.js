$(document).ready(function() {
	
	// クリックイベントの委譲（常時存在する要素に対してイベントを付与する）
	$('body').on('click', '#copyTextBtn', async function() {
		const text = $('#genPassword').text();
		
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