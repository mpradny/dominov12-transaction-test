package net.pristo.domino.transtest;

import lotus.domino.Database;
import lotus.domino.Document;
import lotus.domino.NotesException;
import lotus.domino.View;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestRunner {

    public List<String> Test01(Database db){
        List<String> log = new ArrayList<>();
        try {
            final String countFormula = "Form=\"Doc\"";
            log.add("Start count " + GetDocCount(db,countFormula));
            db.transactionBegin();
            Document doc = db.createDocument();
            doc.replaceItemValue("Form", "Doc");
            doc.replaceItemValue("Subject", "SingleTransactionTest");
            doc.save();
            log.add("Document saved");
            log.add("Middle count " + GetDocCount(db,countFormula));
            db.transactionCommit();
            log.add("Transaction committed");
            log.add("End count " + GetDocCount(db,countFormula));

        } catch (NotesException e){
            log.add("ERROR:" + e.toString());
            e.printStackTrace();
        }
        return log;
    }

    public List<String> Test02(Database db){
        List<String> log = new ArrayList<>();
        try {
            final String countFormula = "Form=\"Doc\"";
            log.add("Start count " + GetDocCount(db,countFormula));
            db.transactionBegin();
            Document doc = db.createDocument();
            doc.replaceItemValue("Form", "Doc");
            doc.replaceItemValue("Subject", "SingleTransactionTest");
            doc.save();
            log.add("Document saved");
            log.add("Middle count " + GetDocCount(db,countFormula));
            db.transactionRollback();
            log.add("Transaction rolled back");
            log.add("End count " + GetDocCount(db,countFormula));

        } catch (NotesException e){
            log.add("ERROR:" + e.toString());
            e.printStackTrace();
        }
        return log;
    }

    public List<String> Test03(Database db){
        List<String> log = new ArrayList<>();
        try {
            final String countFormula = "Form=\"Doc\"";
            log.add("Start count " + GetDocCount(db,countFormula));
            db.transactionBegin();
            Document doc = db.createDocument();
            doc.replaceItemValue("Form", "Doc");
            doc.replaceItemValue("Subject", "SingleTransactionTest");
            doc.save();
            log.add("Document saved");
            log.add("Middle count " + GetDocCount(db,countFormula));
            log.add("Starting sleep - " + new Date());
            Thread.sleep(5000);
            db.transactionRollback();
            log.add("Transaction rolled back");
            log.add("End count " + GetDocCount(db,countFormula));

        } catch (NotesException | InterruptedException e){
            log.add("ERROR:" + e.toString());
            e.printStackTrace();
        }
        return log;
    }

    public List<String> Test04(Database db){
        List<String> log = new ArrayList<>();
        try {
            final String countFormula = "Form=\"Doc\"";
            log.add("Start count " + GetDocCount(db,countFormula));
            db.transactionBegin();
            log.add("Start count view " + GetDocCountView(db,"Docs"));
            Document doc = db.createDocument();
            doc.replaceItemValue("Form", "Doc");
            doc.replaceItemValue("Subject", "SingleTransactionTest");
            doc.save();
            log.add("Document saved");
            log.add("Middle count search " + GetDocCount(db,countFormula));
            log.add("Middle count view " + GetDocCountView(db,"Docs"));
            log.add("Starting sleep - " + new Date());
            Thread.sleep(5000);
            db.transactionRollback();
            log.add("Transaction rolled back");
            log.add("End count " + GetDocCount(db,countFormula));

        } catch (NotesException | InterruptedException e){
            log.add("ERROR:" + e.toString());
            e.printStackTrace();
        }
        return log;
    }



    public Integer GetDocCount(Database db, String formula) throws NotesException {
        return db.search(formula).getCount();

    }

    public Integer GetDocCountView(Database db, String viewName) throws NotesException {
        View v = db.getView(viewName);
        try {
            return v.getAllEntries().getCount();
        } finally {
            v.recycle();
        }

    }
}
