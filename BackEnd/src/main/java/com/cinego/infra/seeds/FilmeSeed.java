package com.cinego.infra.seeds;

import com.cinego.domain.enums.StatusFilme;
import com.cinego.domain.entities.Filme;
import com.cinego.domain.repositories.FilmeRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Tag(name = "Filme Data", description = "Injeção automatica de dados no banco na tabela de filmes caso ela esteja vazia. Usada somente em ambiente de testes")
@Component
public class FilmeSeed {

    @Autowired
    private FilmeRepository filmeRepository;

    @PostConstruct
    public void LocalFilmeData(){

        if (filmeRepository.count() == 0){

            List<Filme> filmes = List.of(
                    new Filme("O Senhor dos Anéis: A Sociedade do Anel", "Fantasia/Aventura", "Um jovem hobbit chamado Frodo precisa destruir um anel poderoso antes que ele caia nas mãos de Sauron.", StatusFilme.DESATIVADO),
                    new Filme("Jurassic Park", "Ficção Científica/Aventura", "Um parque temático onde dinossauros são trazidos de volta à vida se torna um desastre quando os sistemas de segurança falham.", StatusFilme.ALUGADO),
                    new Filme("Harry Potter e a Câmara Secreta", "Fantasia/Aventura", "Um garoto de 11 anos descobre que é um bruxo e começa sua educação mágica em Hogwarts.", StatusFilme.DESATIVADO),
                    new Filme("Blade Runner", "Ficção Científica", "Um detetive particular precisa resolver o mistério por trás de uma série de assassinatos em Los Angeles.", StatusFilme.ALUGADO),
                    new Filme("As Crônicas de Nárnia: O Leão, a Feiticeira e o Guarda-Roupa", "Fantasia/Aventura", "Um jovem garoto é transportado para um mundo mágico cheio de criaturas fantásticas e desafios.", StatusFilme.DESATIVADO),
                    new Filme("Batman: O Cavaleiro das Trevas", "Ação/Heróis", "Um bilionário usa sua tecnologia para combater o crime em Gotham City como o Batman.", StatusFilme.ALUGADO),
                    new Filme("Velozes e Furiosos: Desafio em Tóquio", "Ação", "Dois homens com identidades secretas competem em corridas ilegais nas ruas de Tóquio.", StatusFilme.DESATIVADO),
                    new Filme("Resgate em Alta Tensão", "Ação/Suspense", "Um grupo de mercenários é contratado para salvar reféns em território hostil.", StatusFilme.ALUGADO),
                    new Filme("E.T. - O Extraterrestre", "Ficção Científica/Família", "Um jovem faz amizade com um alienígena perdido na Terra e tenta ajudá-lo a voltar para casa.", StatusFilme.DESATIVADO),
                    new Filme("Homem-Aranha: De Volta ao Lar", "Ação/Heróis", "Um super-herói precisa proteger a cidade de ameaças alienígenas e internas.", StatusFilme.ALUGADO),
                    new Filme("Madrugada dos Mortos", "Terror", "Uma mulher luta para sobreviver em um mundo dominado por zumbis.", StatusFilme.DESATIVADO),
                    new Filme("X-Men", "Ação/Heróis", "Um grupo de jovens descobre que têm poderes especiais e precisam salvar o mundo de um vilão poderoso.", StatusFilme.ALUGADO),
                    new Filme("Indiana Jones e os Caçadores da Arca Perdida", "Aventura/Ação", "Um arqueólogo aventureiro busca artefatos históricos em locais perigosos pelo mundo.", StatusFilme.DESATIVADO),
                    new Filme("O Iluminado", "Terror/Suspense", "Um escritor enfrenta horrores sobrenaturais enquanto está isolado em um hotel durante o inverno.", StatusFilme.ALUGADO),
                    new Filme("O Garoto do Gol", "Esporte/Drama", "Um menino se torna o maior jogador de futebol do mundo enfrentando desafios e rivais.", StatusFilme.DESATIVADO),
                    new Filme("Stranger Things - O Filme", "Fantasia/Ficção Científica", "Um grupo de amigos enfrenta monstros lendários em uma pequena cidade americana.", StatusFilme.ALUGADO),
                    new Filme("Dunkirk", "Guerra/Drama", "Uma história épica de amor e guerra ambientada durante a Segunda Guerra Mundial.", StatusFilme.DESATIVADO),
                    new Filme("Vingadores: Ultimato", "Ação/Heróis", "Um grupo de heróis improváveis se une para salvar o universo de uma ameaça cósmica.", StatusFilme.ALUGADO),
                    new Filme("Percy Jackson e o Ladrão de Raios", "Fantasia/Aventura", "Um jovem herói precisa provar seu valor e enfrentar inimigos lendários.", StatusFilme.DESATIVADO),
                    new Filme("A Culpa é das Estrelas", "Romance/Drama", "Um casal enfrenta desafios em uma história de romance e fantasia.", StatusFilme.ALUGADO),
                    new Filme("Missão Impossível: Protocolo Fantasma", "Ação/Espionagem", "Um agente secreto luta para impedir que uma ameaça global destrua a paz mundial.", StatusFilme.DESATIVADO)
            );

            filmeRepository.saveAll(filmes);
        }
    }
}
