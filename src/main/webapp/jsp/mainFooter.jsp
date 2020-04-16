<script>
    function deleteEntity(url, entityId,callback){
        fetchThis({url, method:'DELETE',body: 'id='+entityId},data=>{crudInformationCallback(data,callback)});
    }

    function createOrUpdateEntity(formId,url,callback){
        sendForm(formId,url,data=>{crudInformationCallback(data,callback)});
    }

    function crudInformationCallback(data,callback){
        let response={error:'Bad server response'};
        try{
            response=JSON.parse(data);
        }catch (e) {}
        if(response.status=='ok'){
            if(typeof callback === 'function') {
                callback();
            }
        }else{
            alert(response.error);
        }
    }

    function sendGetRequest(url){
        fetchThis({url,method:'GET'});
    }

    function sendForm(formId,url='api',callback){
        let formData=new FormData(document.forms[formId]);
        let formAsText=[];
        for(const field of formData.entries()){
            formAsText.push(field[0]+'='+field[1]);
        }
        fetchThis({url,body:formAsText.join('&')},callback);
    }

    function fetchThis({url='api', method='POST', body=''},callback=updatePage){
        let request=method=='GET'?{url}:{
            method,
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body
        };
        fetch(url, request).then(response =>
            response.text()
        ).then(data => {
            callback(data);
        })
    };

    function updatePage(data){
        let root=document.getElementById('root');
        root.innerHTML=data;
    }
</script>

</body>
</html>
