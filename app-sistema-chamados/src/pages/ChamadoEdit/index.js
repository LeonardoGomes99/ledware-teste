import React, { useState,useEffect } from 'react';
import api from '../../services/api';
import { Link,useParams } from 'react-router-dom' 

import '../../styles/Chamado/app.css';

import Header from '../../components/Header';

const Swal = require('sweetalert2')


function ChamadoEdit () {

    const { id } = useParams();
    const [chamado,setChamado] = useState({});
    const [interacao, setInteracao] = useState();
    const [interacaoArchives, setInteracaoArchives] = useState();

    function chamadoInput(e){
        let attribute = e.target.name;
        let value = e.target.value;
        let newChamadoValues = [...chamado];
        newChamadoValues[attribute] = value;
        console.log(newChamadoValues);
        setChamado(newChamadoValues);
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
        function getData() {         
            api.get(`/chamado/${id}`).then((res) => {
                console.log(res.data);
                setChamado(res.data);
            });  
            api.get(`/interacao/getAllByChamadoId/${id}`).then((res) => {
                setInteracao(res.data);
            });               
            api.get(`/MinIO/all/${id}`).then((res) => {
                console.log(res.data);
                setInteracaoArchives(res.data);
            });       
        }
        getData();
    },[])


    function ListaArquivos(interacaoId){
        return(
            interacaoArchives?.map((e,index) => {
                if(e[4] == interacaoId)
                {
                    return(                        
                        <div key={e[0]} className='files-container'>
                            <div className='file'>
                                <a href={e[7]} target="_blank"> {e[1] +`.`+ e[2] } </a>
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

        <div className='main-container'>
            <div className='chamado-container'>
                <div className='chamado-container-group-infos'>
                    <div className='chamado-container-infos'>
                        <label>Assunto</label>
                        <br/>
                        <input type="text" value={chamado.assunto || ""} name="assunto" onChange={e => chamadoInput(e)} ></input>
                    </div>

                    <div className='chamado-container-infos'>
                        <label>Tipo</label>
                        <br/>
                        <input type="text" value={chamado.tipo || ""} name="tipo" onChange={e => chamadoInput(e)}></input>
                    </div>

                    <div className='chamado-container-infos'>
                        <label>Descrição</label>
                        <br/>
                        <textarea type="text" value={chamado.descricao || ""} name="descricao" onChange={e => chamadoInput(e)} ></textarea>
                    </div>
                </div>
            </div>

            <div className='interacao-container'>
            {interacao?.map((e,index) => {
                return(
                    <div className='interacao-container-items' key={e.id}>

                        <div className='interacao-container-item'>
                            <div className='interacao-container-item-values'>
                                <h3>Criado em : </h3>
                                <h4>{e.data}</h4>
                                <h3>Descrição</h3>
                                <textarea id={e.id} data-index={index} type="text" value={e.descricao} name="descricao" onChange={e => interacaoInput(e)} ></textarea>   
                            </div>

                            <div className='archives-container'>

                                <div className='file-uploader'>
                                    <label htmlFor="arquivo">Enviar arquivo</label>
                                    <input type="file" onChange={() => {alert('Subiu')}} accept="application/pdf, image/*" name="arquivo" id="arquivo" />                             
                                </div>

                                <div className='main-files-container'>
                                    {ListaArquivos(e.id)}
                                </div>

                            </div>

                        </div>
                    
                    </div>                                          
                )                                                                             
            })}
            </div>               
            </div>
        </div>
    )


    
}

export default ChamadoEdit