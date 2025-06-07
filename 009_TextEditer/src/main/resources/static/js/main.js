$(document).ready(function() {
	var quill = new Quill('#editor-container', {
		theme: 'snow',
		modules: {
			toolbar: [
				[{ 'font': [] }],
				[{ 'size': ['small', false, 'large', 'huge'] }],
				['bold', 'italic', 'underline', 'strike'],
				[{ 'color': [] }, { 'background': [] }],
				[{ 'list': 'ordered'}, { 'list': 'bullet' }],
				[{ 'align': [] }],
				['clean']
			]
		}
	});
});