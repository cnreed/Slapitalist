package ArtificialIntelligence;


import ArtificialIntelligence.ArtficialIntelligenceInterface.IArtificialIntelligence;
import Game.Board;
import Game.Company;

import java.util.ArrayList;

public class ArtificialIntelligence implements IArtificialIntelligence {

    static int levelAbility;
    AIHand hand;
    WeightBoard weightBoard;
    ArrayList<Company> onBoardCompanies;
    int companiesStarted;
    int companiesMerged;

    // TODO Create the Hand Class
    public ArtificialIntelligence(int levelAbility, Board board,
                                  WeightBoard weightBoard, int handSize) {

        this.levelAbility = levelAbility;
        this.weightBoard = weightBoard;
        this.onBoardCompanies = new ArrayList<Company>();
        this.companiesStarted = 0;
        this.companiesMerged = 0;
        createHand(handSize);
    }

    public void createHand(int handSize) {
        hand = new AIHand(handSize, weightBoard);
    }

    public void addMergeCount() {
        companiesMerged++;
    }

    public void addCompaniesStartedCount() {
        companiesStarted++;
    }

    public void addCompany(Company company) {
        if(!onBoardCompanies.contains(company)) {
            onBoardCompanies.add(company);
        }
    }

    public void removeCompany(Company company) {

    }
}
