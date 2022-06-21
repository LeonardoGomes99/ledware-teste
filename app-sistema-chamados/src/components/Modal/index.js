import { Component,useState,useEffect } from 'react';
import { Link } from 'react-router-dom'
import '../Modal/app.css';
import api from '../../services/api';
const Swal = require('sweetalert2')



function Modal({ setOpenModal, chamadoIdModal, usuarioIdModal, getAllFunction }) {


    const [newDescricao, setNewDescricao] = useState();

    function storeInteracao(){
        const descricao = newDescricao;
        const data = getDateTime();

        const params = {
            "descricao" : descricao,
            "data" : data,
            "chamadoId" : chamadoIdModal,
            "usuarioId" : usuarioIdModal,
        }

        api.post('/interacao', params).then(function(response){
            if(response.status !== 201){
                Swal.fire('Erro','Não foi possível cadastrar esta interação','error');
                return;
            }
            Swal.fire('Cadastrado!','Sua interação foi criada com sucesso.','success') 
            setOpenModal(false)
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

    return(
        <div className="modalBackground">
            <div className="modalContainer">
                <div className="titleCloseBtn">
                <button
                    onClick={() => {
                    setOpenModal(false);
                    }}
                    id="cancelBtn"
                    >
                    X
                </button>
                </div>
                <div className="title">
                <h2>Cadastro de Nova Interação</h2>
                </div>
                <div className="body">
                    <div className='body-content'>
                        <label>Descrição</label>
                        <br/>
                        <input className='body-content-input' value={newDescricao} onChange={(e) => setNewDescricao(e.target.value) } name="descricao"   />
                    </div>
                </div>
                <div className="footer">
                <button
                    onClick={() => {
                    setOpenModal(false);
                    }}
                    id="cancelBtn"
                    >
                    Cancelar
                </button>
                <button onClick={() => {storeInteracao()}} id="cancelBtn" >Cadastrar</button>
                </div>
            </div>
        </div>
    )
    
}

export default Modal;
