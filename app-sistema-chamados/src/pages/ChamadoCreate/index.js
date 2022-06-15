import React, { useState,useEffect } from 'react';
import api from '../../services/api';
import { Link,useParams,useNavigate } from 'react-router-dom' 

import '../../styles/Chamado/app.css';

import Header from '../../components/Header';
import Modal from '../../components/Modal';

const Swal = require('sweetalert2')

function ChamadoCreate () {

    const { id , setId } = useState();

    const [ChamadoId , setChamadoId ] = useState();

    const [chamado,setChamado] = useState({});
    const [interacao, setInteracao] = useState();
    const [interacaoArchives, setInteracaoArchives] = useState();
    const [usuarioId] = useState(window.sessionStorage.getItem("usuarioId"));
    const [fileUpload, setFileUpload] = useState();

    const [modalOpen, setModalOpen] = useState(false);
    const [chamadoState, setChamadoState] = useState(false);
    const navigate = useNavigate();


    function chamadoInput(e){
        
        let attribute = e.target.name;
        let value = e.target.value;
        let newChamado = {...chamado};
        newChamado[attribute] = value;
        setChamado(newChamado);
        
    }

    function interacaoInput(e){
        let index = parseInt(e.target.getAttribute("data-index"));
        let attribute = e.target.name;
        let value = e.target.value;
        let newInteracaoValue = [...interacao];
        newInteracaoValue[index][attribute] = value; 

        setInteracao(newInteracaoValue);
    }

    useEffect(() => {
        function sessionCheck() {         
            if(!window.sessionStorage.getItem("usuarioId")){
              navigate("/");
            };
        }

        function getData() {         
            api.get(`/interacao/getAllByChamadoId/${id}`).then((res) => {
                setInteracao(res.data);
            });               
            api.get(`/MinIO/all/${id}`).then((res) => {
                setInteracaoArchives(res.data);
            });       
        }
        sessionCheck();
        getData();
    },[])

    function getData(chamadoId) { 
        if(chamadoId){
            api.get(`/interacao/getAllByChamadoId/${chamadoId}`).then((res) => {
                setInteracao(res.data);
            });             
            api.get(`/MinIO/all/${chamadoId}`).then((res) => {
                setInteracaoArchives(res.data);
            }); 
        }
        ChamadoId(chamadoId);
         
    }

    function getInteracao(){
        api.get(`/interacao/getAllByChamadoId/${ChamadoId}`).then((res) => {
            setInteracao(res.data);
            api.get(`/MinIO/all/${ChamadoId}`).then((res) => {
                setInteracaoArchives(res.data);                
            });  
        }).then((res) => {
            document.getElementById('interacoes-to-load').innerHTML = "";             
        });              
    }

    function storeChamado(){
        const data = getDateTime();


        const params = {
            "assunto" : chamado['assunto'],
            "tipo" : chamado['tipo'],
            "descricao" : chamado['descricao'],
            "data" : data,
            "usuarioId" : usuarioId
        }

        Swal.fire({
            title: 'Tem certeza que deseja cadastrar esse chamado ?',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Cadastrar!',
            cancelButtonText: 'Cancelar',
          }).then((result) => {
            if (result.isConfirmed) {
                api.post(`/chamado/`,params).then(function(response){
                    if(response.status != 201){
                        Swal.fire('Erro','Não foi possível cadastrar este chamado','error');
                        return;
                    }
                    Swal.fire('Cadastrado!','Seu chamado foi atualizado com sucesso.','success');  
                    getData(response.data.id);  
                    setChamadoState(true);                  
                });
            }
          })
    }

    function updateInteracao(e){

        const interacaoUpdate = interacao.filter(p => p.id == e.target.id)
        const idInteracaoToUpdate = interacaoUpdate[0]['id'];
        const DescricaoInteracaoToUpdate = interacaoUpdate[0]['descricao'];
        const data = getDateTime();

        const params = {
            "descricao" : DescricaoInteracaoToUpdate,
            "data" : data,
            "chamadoId" : ChamadoId,
            "usuarioId" : usuarioId
        }
        
        Swal.fire({
            title: 'Tem certeza que deseja atualizar essa interação?',
            text: "Não poderá reverter!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Atualizar!',
            cancelButtonText: 'Cancelar',
          }).then((result) => {
            if (result.isConfirmed) {
                api.put(`/interacao/${idInteracaoToUpdate}`,params).then(function(response){
                    Swal.fire('Atualizado!','Sua interação foi atualizada com sucesso.','success')                    
                    getData(ChamadoId);
                }).catch(function(res) {
                    Swal.fire('Erro','Não foi possível atualizar essa interação','error');
                });
            }
          })
    }

    function deleteInteracao(event){

        const interacaoDelete = interacao.filter(p => p.id == event.target.id)
        const idInteracaoToDeleteAll = interacaoDelete[0]['id'];

        Swal.fire({
        title: 'Tem certeza que deseja excluir essa interação?',
            text: "Não poderá reverter!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#d33',
            cancelButtonColor: '#3085d6',
            confirmButtonText: 'Excluir!',
            cancelButtonText: 'Cancelar',
          }).then((result) => {
            if (result.isConfirmed) {
                api.delete(`/MinIO/deleteAllByInteracaoId/${idInteracaoToDeleteAll}`).then(function(response){
                    api.delete(`/interacao/${idInteracaoToDeleteAll}`).then(function(response){
                        Swal.fire('Excluído!','Sua interação foi excluída com sucesso.','success')                    
                        getData(ChamadoId);
                    }).catch(function(res) {
                        Swal.fire('Erro','Não foi possível excluir essa interação','error');
                    });
                }).catch(function(res) {
                    Swal.fire('Erro','Não foi possível excluir essa interação','error');
                });

                
            }
          })
    }

    function getDateTime(){
        var currentdate = new Date(); 
        var minutes = (currentdate.getMinutes() < 10? '0' : '') + currentdate.getMinutes();
        var datetime =  currentdate.getDate() + "/"
                        + (currentdate.getMonth()+1)  + "/" 
                        + currentdate.getFullYear() + " "  
                        + currentdate.getHours() + ":"  
                        + minutes;
        return datetime
    }

    function convertToBase64(event){ 
        var file = (event.target.files[0]);
        Swal.fire({
            title: `Deseja realizar Upload deste arquivo?`,
            text: ` ${file.name} `,
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Sim, desejo realizar Upload deste arquivo',
            cancelButtonText: 'Não, não desejo subir esse arquivo'
          }).then((result) => {
            
            if (file) {
                    var extension = file.name.split('.').pop().toLowerCase(),
                        isSuccess = ['pdf','png'].indexOf(extension) > -1;

                    var name = file.name.split('.').slice(0, -1).join('.')

                    if(isSuccess){
                        var reader = new FileReader();   
                        reader.onload = function () {
                            var base64Archive = reader.result.replace("data:", "").replace(/^.+,/, "");
                            SendArchiveToServer(base64Archive,name,extension,event);
                        }
                        reader.readAsDataURL(file);
                    }else{
                        Swal.fire('Formato de arquivo incompátivel, Tente em formato PDF ou PNG');
                    }   
            } 
          }) 
        
        event.target.value = '';
    }

    function SendArchiveToServer(base64Archive,name,extension,event){
        const usuarioIdArchive =  usuarioId;
        const chamadoIdArchive = ChamadoId;
        const interacaoIdArchive = (event.target.getAttribute('data-id'));

        const params = {
            "nomeArquivo" : name,
            "tipoArquivo" : extension,
            "urlArquivo" : ".",
            "interacaoId" : interacaoIdArchive,
            "chamadoId" : chamadoIdArchive,
            "usuarioId" : usuarioIdArchive,
            "arquivo" : base64Archive
        }

        api.post('/MinIO',params).then(function(response) {
            getData(ChamadoId);
            Swal.fire({
                position: 'top-end',
                icon: 'success',
                title: 'Upload de arquivo realizado com sucesso!!',
                showConfirmButton: false,
                timer: 1500
            })
        }).catch(function(res) {
            Swal.fire('Erro ao enviar o arquivo');
        });
    }

    function deleteArchive(id){
        
        Swal.fire({
            title: 'Tem certeza que deseja excluir este arquivo?',
            text: "Não será possivel reverter",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonText: 'SIM, excluir',
            cancelButtonText: 'NÃO, desejo cancelar',
            reverseButtons: true
          }).then((result) => {
            if (result.isConfirmed) {
                
                api.delete(`/MinIO/${id}`).then(function(response){
                    Swal.fire('Excluído!','Seu arquivo foi excluído com sucesso.','success')                    
                    getData();
                }).catch(function(res) {
                    Swal.fire('Erro','Não foi possível excluir o arquivo','error');
                });
              
            } else if (
              /* Read more about handling dismissals below */
              result.dismiss === Swal.DismissReason.cancel
            ) {
                Swal.fire(
                'Cancelado',
                'Operação cancelada',
                'error'
              )
            }
          })
    }

    function ListFiles(interacaoId){
        return(
            interacaoArchives?.map((e,index) => {
                if(e[4] === interacaoId)
                {
                    return(                        
                        <div key={e[0]} className='files-container'>
                            <div className='delete-file-container'>
                                <span onClick={() => deleteArchive(e[0])} className='delete-file'>X</span>
                            </div>
                            <div className='file'>
                                <a href={e[7]} target="_blank" rel="noreferrer" > {e[1] +`.`+ e[2] } </a>
                            </div>
                        </div>                                  
                    )
                }                                                                         
            })
        )        
    }



    return(
        
        <div>

        <Header/>
        {modalOpen && <Modal setOpenModal={setModalOpen} chamadoIdModal={ChamadoId} usuarioIdModal={usuarioId} getAllFunction={getData()} />}

        <div className='main-container'>
            <div className='chamado-container'>
                <div className='chamado-container-group-infos'>
                    
                    <div className='chamado-container-infos'>

                        <div className='chamado-container-infos-label'>
                            <label>Assunto</label>
                        </div>

                        <div className='chamado-container-infos-textarea'>
                            <input type="text" value={chamado.assunto || ""} name="assunto" onChange={e => chamadoInput(e)} disabled={chamadoState}></input>
                        </div>

                    </div>

                    <div className='chamado-container-infos'>

                        <div className='chamado-container-infos-label'>
                            <label>Tipo</label>
                        </div>

                        <div className='chamado-container-infos-textarea'>
                            <input type="text" value={chamado.tipo || ""} name="tipo" onChange={e => chamadoInput(e)} disabled={chamadoState}></input>
                        </div>

                    </div>

                    <div className='chamado-container-infos'>

                        <div className='chamado-container-infos-label'>
                            <label>Descrição</label>
                        </div>

                        <div className='chamado-container-infos-textarea'>
                            <textarea type="text" value={chamado.descricao || ""} name="descricao" onChange={e => chamadoInput(e)} disabled={chamadoState} ></textarea>
                        </div>

                        <div className='submit-changes-controller'>
                                <button onClick={()=> storeChamado()} disabled={chamadoState} >Cadastrar Informações</button>                                                        
                        </div>

                    </div>
                </div>
            </div>

            <div className='interacao-container'>
                <div className='interacao-controls'>
                    <div className='refresh-interacao-container'>
                        <button className='refresh-interacao-button' onClick={() => {getData()}} > Atualizar </button>        
                    </div>
                    <div className='create-new-interacao-container'>
                        <button className='create-new-interacao-button' onClick={() => {setModalOpen(true);}} > + Cadastrar Nova Interação</button>        
                    </div>
                </div>
            <hr/>
            <div id="interacoes-to-load">
                {interacao?.map((e,index) => {
                    return(
                        <div className='interacao-container-items' key={index}>
                            <div className='delete-file-container'>
                                <span id={e.id} onClick={(event) => deleteInteracao(event)} className='delete-file'>X</span>
                            </div>
                            <div className='interacao-container-item'>
                                <div className='interacao-container-item-values'>
                                    <h3>Criado em : </h3>
                                    <h4>{e.data}</h4>
                                    <h3>Descrição</h3>
                                    <textarea id={e.id} data-index={index} type="text" value={e.descricao} name="descricao" onChange={e => interacaoInput(e)} ></textarea>   
                                </div>

                                <div className='submit-changes-controller'>
                                    <button id={e.id} onClick={(e) => updateInteracao(e)} >Atualizar Informações</button>                                                        
                                </div>

                                <div className='archives-container'>

                                    <div className='file-uploader'>
                                        <label htmlFor={`arquivo_${index}`}>Enviar arquivo</label>
                                        <input data-id={e.id} type="file" name={`arquivo_${index}`} id={`arquivo_${index}`} onChange={event => { convertToBase64(event) }} accept="application/pdf, image/*" />                             
                                    </div>

                                    <div className='main-files-container'>
                                        {ListFiles(e.id)}
                                    </div>

                                </div>

                            </div>
                        
                        </div>                                          
                    )                                                                             
                })}
            </div>
            </div>               
            </div>
        </div>
    )


    
}

export default ChamadoCreate