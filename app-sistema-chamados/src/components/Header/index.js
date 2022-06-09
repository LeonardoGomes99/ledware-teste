import { Component } from 'react';
import { Link } from 'react-router-dom'
import '../Header/app.css';

export default class Header extends Component {
    render(){
        return(
            <header>
                <h3 className="logo">Sistema de Chamados</h3>
                <Link className="favoritos" to="/favoritos">Meus Chamados</Link>
            </header>
        )
    }
    
}
    


