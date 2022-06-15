import React, { useState,useEffect } from 'react';
import { Link,useNavigate } from 'react-router-dom'
import api from '../../services/api';
import '../Home/app.css';

const Swal = require('sweetalert2')



function Home() {

    const [email, setEmail] = useState();
    const [senha, setSenha] = useState();
    const navigate = useNavigate();


    useEffect(() => {

      function sessionCheck() {         
        if(window.sessionStorage.getItem("usuarioId")){
          navigate("/dashboard");
        };
      }
      sessionCheck();
    },[])

    function login(){
      const params = {
        "email" : email,
        "senha" : senha,
      }


      api.post('/usuario/login',params).then(function(response){
        window.sessionStorage.setItem("usuarioId", response.data.id);
        navigate("/dashboard");
        
      }).catch(function(response){
        Swal.alert('Erro','Login inválido','error');
      });

    }

    return (
      <div>
          <h1>Bem Vindo a Home</h1>


          <label>Email</label>
          <input type="email" value={email} onChange={(e) => setEmail(e.target.value)}></input>

          <label>Senha</label>
          <input type='password' value={senha} onChange={(e) => setSenha(e.target.value)}></input>

          <button onClick={() => login()} >Login</button>

      </div>      
    );
  }
  
  export default Home;
  