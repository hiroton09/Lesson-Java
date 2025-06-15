$(document).ready(function() {

	const N_MSG_001 = "ファイルを読み込みました：";
	const E_MSG_001 = "ファイルを選択してください。";
	const E_MSG_002 = "ファイルの読み込みに失敗しました：";
	const E_MSG_003 = "ファイルリストの取得に失敗しました。";
	const E_MSG_004 = "ファイルが見つかりません：";
	const E_MSG_005 = "ファイル名を入力してください。";
	const E_MSG_006 = "削除するファイルを選択してください。";
	const E_MSG_007 = "ファイルの削除に失敗しました。";
	const E_MSG_008 = "ファイルの保存に失敗しました。";
	const I_MSG_001 = "ファイルを選択してください";
	const I_MSG_002 = "現在のファイルは保存されていません。\n破棄して新規作成しますか？";
	const I_MSG_003 = "新しいドキュメントを作成しました。";
	const I_MSG_004 = "現在のファイルは保存されていません。\n破棄して読み込みますか？";
	const I_MSG_005 = "本当に";
	const I_MSG_006 = "を削除しますか？";
	const I_MSG_007 = "ファイルを閉じました。";
	const W_MSG_001 = "ファイルを上書き保存するには、まず「名前を付けて保存」してください。";

	const TIMEOUT = 3000;
	const STATUS_404 = 404;
	const INFO = "info";
	const DANGER = "danger";
	const SUCCESS = "success";
	const WARNING = "warning";

	var fileList = $('#fileList');
	var message = $('#message');
	var saveFileNameModal = $('#saveFileNameModal');
	var newFileNameInput = $('#newFileNameInput');
	var loadFileButton = $('#loadFileButton');
	var saveAsButton = $('#saveAsButton');
	var saveButton = $('#saveButton');
	var createNewButton = $('#createNewButton');
	var closeButton = $('#closeButton');
	var deleteButton = $('#deleteButton');
	var confirmSaveFileNameButton = $('#confirmSaveFileNameButton');

	// テキストエディタ画面の設定
	var quill = new Quill('#editorContainer', {
		theme: 'snow',
		modules: {
			toolbar: [
				[{ 'font': [] }],
				[{ 'size': ['small', false, 'large', 'huge'] }],
				['bold', 'italic', 'underline', 'strike'],
				[{ 'color': [] }, { 'background': [] }],
				[{ 'list': 'ordered' }, { 'list': 'bullet' }],
				[{ 'align': [] }],
				['clean']
			]
		}
	});

	var currentFileName = null;
	var isFileModified = false;

	// ********************************
	// * エディタの内容が変更されたらフラグを立てる
	// ********************************
	quill.on('text-change', function() {
		if (!isFileModified) {
			isFileModified = true;
			updateButtonStates();
		}
	});

	// ********************************
	// * 保存ファイルリスト取得処理
	// ********************************
	function refreshFileList() {
		// ファイルリストクリア
		fileList.empty();

		// ファイルリスト初期値設定
		fileList.append($('<option>', { value: '', text: I_MSG_001 }));

		// ファイルリスト取得
		$.get('api/documents/list', function(data) {
			$.each(data, function(index, fileName) {
				const option = $('<option>', {
					value: fileName,
					text: fileName
				});

				// 現在開いているファイルを選択状態にする
				if (fileName === currentFileName) {
					option.prop('selected', true);
				}

				fileList.append(option);
			});
		}).fail(function() {
			showMessage(E_MSG_003, DANGER);
		});
	}

	// ********************************
	// * ボタン状態の更新処理
	// ********************************
	function updateButtonStates() {
		const hasFileOpened = currentFileName != null;
		const isSelectedInList = fileList.val() != '';

		// ファイルが開いていて変更があれば有効
		saveButton.prop('disabled', !hasFileOpened || !isFileModified);

		// リストでファイルが選択されていれば有効
		deleteButton.prop('disabled', !isSelectedInList);

		// ファイルが開いていれば有効
		closeButton.prop('disabled', !hasFileOpened);
	}

	// ********************************
	// * メッセージ表示処理
	// ********************************
	function showMessage(text, type) {
		message.removeClass().addClass(`alert alert-${type}`).text(text);
		setTimeout(() => {
			message.text('');
			message.removeClass();
		}, TIMEOUT);
	}

	// ********************************
	// * 初期化処理
	// ********************************
	refreshFileList();
	updateButtonStates();

	// ********************************
	// * 新規作成処理
	// ********************************
	createNewButton.on('click', function() {
		// ファイルの変更を破棄して新規作成するか確認
		if (isFileModified && currentFileName) {
			if (!confirm(I_MSG_002)) {
				return;
			}
		}

		// 初期処理
		quill.root.innerHTML = '';
		currentFileName = null;
		isFileModified = false;
		fileList.val('');
		showMessage(I_MSG_003, INFO);
		updateButtonStates();
	});

	// ********************************
	// * ファイル読み込み処理
	// ********************************
	loadFileButton.on('click', function() {

		// 現在開いているファイル名
		let selectedFileName = fileList.val();

		// ファイル名が選択されていない場合
		if (!selectedFileName) {
			showMessage(E_MSG_001, DANGER);
			return;
		}

		// 開いているファイルが変更されている場合
		if (isFileModified && currentFileName !== selectedFileName) {
			if (!confirm(I_MSG_004)) {
				return;
			}
		}

		// ファイル取得処理
		$.get('/api/documents/' + selectedFileName, function(data) {
			quill.root.innerHTML = data;
			currentFileName = normalizeFileName(selectedFileName);;
			isFileModified = false;
			showMessage(N_MSG_001 + selectedFileName, SUCCESS);
			updateButtonStates();
		}).fail(function(jqXHR) {
			var errMsg = E_MSG_002;
			if (jqXHR.status === STATUS_404) {
				errMsg = E_MSG_004
			}
			showMessage(errMsg, DANGER);
		});
	});

	// ********************************
	// * 保存処理
	// ********************************
	saveButton.on('click', function() {
		if (currentFileName) {
			saveFile(currentFileName);
		} else {
			// ファイル名が不明な場合は別名保存ダイアログを表示
			showMessage(W_MSG_001, WARNING);
			saveFileNameModal.modal('show');
		}
	});

	// ********************************
	// * 別名保存処理
	// ********************************
	saveAsButton.on('click', function() {
		newFileNameInput.val(currentFileName || '');
		saveFileNameModal.modal('show');
	});

	// ********************************
	// * モーダル内の保存処理
	// ********************************
	confirmSaveFileNameButton.on('click', function() {
		var fileName = newFileNameInput.val().trim();
		if (!fileName) {
			showMessage(E_MSG_005, DANGER);
			return;
		}
		saveFileNameModal.modal('hide');
		saveFile(fileName);
	});

	// ********************************
	// * ファイル削除処理
	// ********************************
	deleteButton.on('click', function() {
		var selectedFileName = fileList.val();
		if (!selectedFileName) {
			showMessage(E_MSG_006, DANGER);
			return;
		}

		if (!confirm(I_MSG_005 + selectedFileName + I_MSG_006)) {
			return;
		}

		$.ajax({
			url: '/api/documents/' + selectedFileName,
			type: 'DELETE',
			success: function(response) {
				showMessage(response, SUCCESS);
				refreshFileList();
				if (currentFileName === selectedFileName) {
					closeButton.click();
				}
			},
			error: function(xhr, status, error) {
				var errMsg = E_MSG_007;
				if (xhr.status === STATUS_404) {
					errMsg = E_MSG_004 + selectedFileName;
				} else if (xhr.responseText) {
					errMsg += '：' + xhr.responseText;
				}
				showMessage(errMsg, DANGER);
			}
		});
	});

	// ********************************
	// * ファイル閉じる処理
	// ********************************
	closeButton.on('click', function() {
		if (isFileModified && currentFileName) {
			if (!confirm(I_MSG_002)) {
				return;
			}
		}

		// 初期表示
		quill.root.innerHTML = '';
		currentFileName = null;
		isFileModified = false;
		fileList.val('');
		showMessage(I_MSG_007, INFO);
		updateButtonStates();
	});

	// ********************************
	// * ヘルパー関数
	// ********************************
	// 保存処理
	function saveFile(fileName) {
		var editorContent = quill.root.innerHTML;
		$.ajax({
			url: '/api/documents/' + fileName,
			type: 'POST',
			contentType: 'text/html; charset=UTF-8',
			data: editorContent,
			success: function(response) {
				showMessage(response, SUCCESS);
				currentFileName = normalizeFileName(fileName);
				isFileModified = false;
				refreshFileList();
				updateButtonStates();
			},
			error: function(xhr, status, error) {
				var errMsg = E_MSG_008;
				if (xhr.responseText) {
					errMsg += '：' + xhr.responseText;
				}
				showMessage(errMsg, DANGER);
			}
		});
	}

	// 拡張子なしのファイルをフォーマット処理
	function normalizeFileName(fileName) {
		// ファイル名が .html または .txt で終わっていない場合に .html を追加する
		if (!fileName.match(/\.(html|txt)$/i)) {
			return fileName + ".html";
		}
		return fileName;
	}
});