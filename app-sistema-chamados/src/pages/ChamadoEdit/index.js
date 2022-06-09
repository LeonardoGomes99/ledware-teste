import React, { useState,useEffect } from 'react';
import api from '../../services/api';
import { useParams } from 'react-router-dom' 

import '../../styles/Chamado/app.css';
const Swal = require('sweetalert2')


function ChamadoEdit () {

    const { id } = useParams();
    const [chamado,setChamado] = useState({});
    const [interacao, setInteracao] = useState();

    useEffect(() => {
        function getData() {         
            api.get(`/chamado/${id}`).then((res) => {
                setChamado(res.data);
            });  
            api.get(`/interacao/getAllByChamadoId/${id}`).then((res) => {
                setInteracao(res.data);
            });                       
        }

    },[])
    



   

    return(
        <div>
            
        </div>
    )
}

export default ChamadoEdit