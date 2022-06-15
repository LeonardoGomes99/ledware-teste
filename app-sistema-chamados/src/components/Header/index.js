import { Component } from 'react';
import { Link,useNavigate } from 'react-router-dom'
import '../Header/app.css';

export default class Header extends Component {
    render(){
        return(
            <header>
                <h3 className="logo">Sistema de Chamados</h3>
                <Link className="botoes" to="/dashboard">Meus Chamados</Link>
                <button className='botoes' onClick={() => {sessionStorage.clear();window.location.href='/';}}> Sair </button>

            </header>
        )
    }
    
}
    


